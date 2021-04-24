package com.xcky.util;

import com.xcky.config.UnionPayConfig;
import com.xcky.config.UnionPaySignCertConfig;
import com.xcky.util.encry.Base64Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.spec.RSAPublicKeySpec;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 证书工具类，主要用于对证书的加载和使用
 *
 * @author lbchen
 */
@Slf4j
@Getter
@Component
public class CertUtil {
    private static Pattern signPattern = Pattern.compile("\\s*\"sign\"\\s*:\\s*\"([^\"]*)\"\\s*");
    private static Pattern dataPattern = Pattern.compile("\\s*\"data\"\\s*:\\s*\"([^\"]*)\"\\s*");
    private static Pattern certPattern = Pattern.compile("cert_id=(\\d*)");

    private UnionPayConfig unionPayConfig;
    private UnionPaySignCertConfig signCertConfig;
    /**
     * 证书容器，存储对商户请求报文签名私钥证书.
     */
    private static KeyStore keyStore = null;
    /**
     * 磁道加密公钥
     */
    public static PublicKey encryptTrackPublicKey = null;

    /**
     * 敏感信息加密公钥证书
     */
    private static X509Certificate encryptCert = null;

    /**
     * 验证银联返回报文签名证书.
     */
    private static X509Certificate validateCert = null;
    /**
     * 验签中级证书
     */
    private static X509Certificate middleCert = null;
    /**
     * 验签根证书
     */
    private static X509Certificate rootCert = null;
    /**
     * 验证银联返回报文签名的公钥证书存储Map.
     */
    private static Map<String, X509Certificate> certMap = new HashMap<>(16);
    /**
     * 商户私钥存储Map
     */
    private final static Map<String, KeyStore> KEY_STORE_MAP = new ConcurrentHashMap<>(16);

    public CertUtil instance = null;

    /**
     * 初始化所有证书
     *
     * @param unionPayConfig 云闪付配置
     */
    public CertUtil(UnionPayConfig unionPayConfig) {
        if (null == unionPayConfig) {
            return;
        }
        this.unionPayConfig = unionPayConfig;
        this.signCertConfig = unionPayConfig.getSigncert();
        try {
            //向系统添加BC provider
            addProvider();
            //初始化签名私钥证书
            initSignCert(unionPayConfig.getSignMethod(),
                    signCertConfig.getPath(),
                    signCertConfig.getPwd(),
                    signCertConfig.getType());
            //初始化验签证书的中级证书
            middleCert = initCert(unionPayConfig.getMiddleCertPath());
            //初始化验签证书的根证书
            rootCert = initCert(unionPayConfig.getRootCertPath());
            //初始化加密公钥
            encryptCert = initCert(unionPayConfig.getEncryptCertPath());
            //构建磁道加密公钥
            initTrackKey(unionPayConfig.getEncryptTrackKeyModulus(), unionPayConfig.getEncryptTrackKeyExponent());
        } catch (Exception e) {
            log.error("init失败。（如果是用对称密钥签名的可无视此异常。）", e);
        }
        this.instance = this;
    }

    /**
     * 添加签名，验签，加密算法提供者
     */
    private static void addProvider() {
        if (Security.getProvider(Constants.SECURITY_PROVIDER) == null) {
            log.info("add BC provider");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        } else {
            //解决eclipse调试时tomcat自动重新加载时，BC存在不明原因异常的问题。
            Security.removeProvider("BC");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            log.info("reAdd BC provider");
        }
    }

    /**
     * 用配置文件acp_sdk.properties中配置的私钥路径和密码 加载签名证书
     */
    private void initSignCert(String signMethod, String signCertPath, String signCertPwd, String signCertType) {
        if (!Constants.UNNION_PAY_SIGN_METHOD.equals(signMethod)) {
            log.info("非rsa签名方式，不加载签名证书。");
            return;
        }
        if (signCertPath == null
                || signCertPwd == null
                || signCertType == null) {
            log.warn("signCertPath " + signCertPath + " signCertPwd " + signCertPwd
                    + " signCertType " + signCertType + "为空。 停止加载签名证书。");
            return;
        }
        if (null != keyStore) {
            keyStore = null;
        }
        try {
            keyStore = getKeyInfo(signCertPath, signCertPwd, signCertType);
        } catch (IOException e) {
            log.error("InitSignCert Error", e);
        }
    }

    /**
     * 用配置文件acp_sdk.properties配置路径 加载磁道公钥
     */
    private static void initTrackKey(String encryptTrackKeyModulus, String encryptTrackKeyExponent) {
        if (!StringUtils.isEmpty(encryptTrackKeyModulus) && !StringUtils.isEmpty(encryptTrackKeyExponent)) {
            CertUtil.encryptTrackPublicKey = getPublicKey(encryptTrackKeyModulus, encryptTrackKeyExponent);
            log.info("LoadEncryptTrackKey Successful");
        } else {
            log.warn("WARN: acpsdk.encryptTrackKey.modulus or acpsdk.encryptTrackKey.exponent is empty");
        }
    }

    /**
     * 用配置文件acp_sdk.properties配置路径 加载验证签名证书
     */
    private static void initValidateCertFromDir(String signMethod, String certDir) {
        if (!Constants.UNNION_PAY_SIGN_METHOD.equals(signMethod)) {
            log.info("非rsa签名方式，不加载验签证书。");
            return;
        }
        certMap.clear();
        log.info("加载验证签名证书目录==>" + certDir + " 注：如果请求报文中version=5.1.0那么此验签证书目录使用不到，可以不需要设置（version=5.0.0必须设置）。");
        if (StringUtils.isEmpty(certDir)) {
            log.error("证书目录配置不能为空");
            return;
        }
        CertificateFactory cf;
        FileInputStream in = null;
        try {
            cf = CertificateFactory.getInstance("X.509", Constants.SECURITY_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        File fileDir = new File(certDir);
        File[] files = fileDir.listFiles();
        if (null != files && files.length > 0) {
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.endsWith("pfx")) {
                    continue;
                }
                try {
                    in = new FileInputStream(file.getAbsolutePath());
                    Certificate x509Cert = cf.generateCertificate(in);
                    if (x509Cert instanceof X509Certificate) {
                        validateCert = (X509Certificate) cf.generateCertificate(in);
                        if (validateCert == null) {
                            log.error("Load verify cert error, " + file.getAbsolutePath() + " has error cert content.");
                            continue;
                        }
                        certMap.put(validateCert.getSerialNumber().toString(),
                                validateCert);
                        // 打印证书加载信息,供测试阶段调试
                        log.info("[" + file.getAbsolutePath() + "][CertId="
                                + validateCert.getSerialNumber().toString() + "]");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != in) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            log.error(e.toString());
                        }
                    }
                }
            }
        }
        log.info("LoadVerifyCert Finish");
    }

    /**
     * 用给定的路径和密码 加载签名证书，并保存到certKeyStoreMap
     *
     * @param certFilePath 证书文件路径
     * @param certPwd      证书密码
     */
    private static void loadSignCert(String certFilePath, String certPwd) {
        KeyStore keyStore;
        try {
            keyStore = getKeyInfo(certFilePath, certPwd, "PKCS12");
            KEY_STORE_MAP.put(certFilePath, keyStore);
            log.info("LoadRsaCert Successful");
        } catch (IOException e) {
            log.error("LoadRsaCert Error", e);
        }
    }


    /**
     * 通过指定路径的私钥证书  获取PrivateKey对象
     *
     * @param certPath 证书路径
     * @param certPwd  证书密钥
     * @return 证书私钥
     */
    public static PrivateKey getSignCertPrivateKeyByStoreMap(String certPath, String certPwd) {
        if (!KEY_STORE_MAP.containsKey(certPath)) {
            loadSignCert(certPath, certPwd);
        }
        try {
            Enumeration<String> aliasenum = KEY_STORE_MAP.get(certPath).aliases();
            String keyAlias = null;
            if (aliasenum.hasMoreElements()) {
                keyAlias = aliasenum.nextElement();
            }
            PrivateKey privateKey = (PrivateKey) KEY_STORE_MAP.get(certPath)
                    .getKey(keyAlias, certPwd.toCharArray());
            return privateKey;
        } catch (Exception e) {
            log.error("getSignCertPrivateKeyByStoreMap Error", e);
            return null;
        }
    }

    /**
     * 通过证书路径初始化为公钥证书
     *
     * @param path 证书路径
     * @return 证书对象
     */
    private static X509Certificate initCert(String path) {
        log.info("加载证书==>" + path);
        if (!StringUtils.isEmpty(path)) {
            X509Certificate encryptCertTemp = null;
            CertificateFactory cf;
            FileInputStream in = null;
            try {
                cf = CertificateFactory.getInstance("X.509", "BC");
                in = new FileInputStream(path);
                encryptCertTemp = (X509Certificate) cf.generateCertificate(in);
                // 打印证书加载信息,供测试阶段调试
                log.info("[" + path + "][CertId=" + encryptCertTemp.getSerialNumber() + "]");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        log.error(e.toString());
                    }
                }
            }
            return encryptCertTemp;
        } else {
            log.warn("path is empty: " + path);
        }
        return null;
    }

    /**
     * 通过keyStore 获取私钥签名证书PrivateKey对象
     *
     * @param signCertPwd 证书密钥
     * @return 证书私钥
     */
    public PrivateKey getSignCertPrivateKey(String signCertPwd) {
        try {
            Enumeration<String> aliasEnum = keyStore.aliases();
            String keyAlias = null;
            if (aliasEnum.hasMoreElements()) {
                keyAlias = aliasEnum.nextElement();
            }
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias,
                    signCertPwd.toCharArray());
            return privateKey;
        } catch (Exception e) {
            log.error("getSignCertPrivateKey Error", e);
            return null;
        }
    }

    /**
     * 重置敏感信息加密证书公钥
     */
    public void resetEncryptCertPublicKey() {
        encryptCert = null;
    }

    /**
     * 通过certId获取验签证书Map中对应证书PublicKey
     *
     * @param certId 证书物理序号
     * @return 通过证书编号获取到的公钥
     */
    public PublicKey getValidatePublicKey(String certId, String signatureMethod, String certDir) {
        X509Certificate cf;
        if (certMap.containsKey(certId)) {
            // 存在certId对应的证书对象
            cf = certMap.get(certId);
            return cf.getPublicKey();
        } else {
            // 不存在则重新Load证书文件目录
            initValidateCertFromDir(signatureMethod, certDir);
            if (certMap.containsKey(certId)) {
                // 存在certId对应的证书对象
                cf = certMap.get(certId);
                return cf.getPublicKey();
            } else {
                log.error("缺少certId=[" + certId + "]对应的验签证书.");
                return null;
            }
        }
    }

    /**
     * 获取配置文件acp_sdk.properties中配置的签名私钥证书certId
     *
     * @return 证书的物理编号
     */
    public String getSignCertId() {
        try {
            Enumeration<String> aliasenum = keyStore.aliases();
            String keyAlias = null;
            if (aliasenum.hasMoreElements()) {
                keyAlias = aliasenum.nextElement();
            }
            X509Certificate cert = (X509Certificate) keyStore
                    .getCertificate(keyAlias);
            return getCertId(cert);
        } catch (Exception e) {
            log.error("getSignCertId Error", e);
            return null;
        }
    }

    /**
     * 获取敏感信息加密证书的certId
     *
     * @param certificate 证书对象
     * @return 证书序列字符串
     */
    public String getCertId(X509Certificate certificate) {
        if (null == certificate) {
            return null;
        } else {
            return certificate.getSerialNumber().toString();
        }
    }

    /**
     * 将签名私钥证书文件读取为证书存储对象
     *
     * @param pfxkeyfile 证书文件名
     * @param keypwd     证书密码
     * @param type       证书类型
     * @return 证书对象
     * @throws IOException
     */
    private static KeyStore getKeyInfo(String pfxkeyfile, String keypwd,
                                       String type) throws IOException {
        log.info("加载签名证书==>" + pfxkeyfile);
        FileInputStream fis = null;
        try {
            KeyStore ks = KeyStore.getInstance(type, "BC");
            log.info("Load RSA CertPath=[" + pfxkeyfile + "],Pwd=[" + keypwd + "],type=[" + type + "]");
            fis = new FileInputStream(pfxkeyfile);
            char[] nPassword;
            nPassword = null == keypwd || "".equals(keypwd.trim()) ? null : keypwd.toCharArray();
            if (null != ks) {
                ks.load(fis, nPassword);
            }
            return ks;
        } catch (Exception e) {
            log.error("getKeyInfo Error", e);
            return null;
        } finally {
            if (null != fis) {
                fis.close();
            }
        }
    }

    /**
     * 通过签名私钥证书路径，密码获取私钥证书certId
     *
     * @param certPath 证书路径
     * @param certPwd  证书密钥
     * @return 证书CERT_ID值
     */
    public static String getCertIdByKeyStoreMap(String certPath, String certPwd) {
        if (!KEY_STORE_MAP.containsKey(certPath)) {
            // 缓存中未查询到,则加载RSA证书
            loadSignCert(certPath, certPwd);
        }
        return getCertIdIdByStore(KEY_STORE_MAP.get(certPath));
    }

    /**
     * 通过keystore获取私钥证书的certId值
     *
     * @param keyStore keyStore
     * @return 证书的certId
     */
    private static String getCertIdIdByStore(KeyStore keyStore) {
        Enumeration<String> aliasenum = null;
        try {
            aliasenum = keyStore.aliases();
            String keyAlias = null;
            if (aliasenum.hasMoreElements()) {
                keyAlias = aliasenum.nextElement();
            }
            X509Certificate cert = (X509Certificate) keyStore
                    .getCertificate(keyAlias);
            return cert.getSerialNumber().toString();
        } catch (KeyStoreException e) {
            log.error("getCertIdIdByStore Error", e);
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA公钥 注意：此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同
     *
     * @param modulus  模
     * @param exponent 指数
     * @return 证书公钥
     */
    private static PublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            log.error("构造RSA公钥失败：" + e);
            return null;
        }
    }

    /**
     * 将字符串转换为X509Certificate对象.
     *
     * @param x509CertString 证书对象字符串
     * @return 证书对象
     */
    public static X509Certificate genCertificateByStr(String x509CertString) {
        X509Certificate x509Cert = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
            InputStream tIn = new ByteArrayInputStream(
                    x509CertString.getBytes("ISO-8859-1"));
            x509Cert = (X509Certificate) cf.generateCertificate(tIn);
        } catch (Exception e) {
            log.error("gen certificate error", e);
        }
        return x509Cert;
    }

    /**
     * 获取证书的CN
     *
     * @param cert 证书对象
     * @return 获取证书标识符
     */
    private static String getIdentitiesFromCertficate(X509Certificate cert) {
        String sdn = cert.getSubjectDN().toString();
        String tPart = "";
        if ((sdn != null)) {
            String[] tSplitStr = sdn.substring(sdn.indexOf("CN=")).split("@");
            if (tSplitStr.length > Constants.TWO && tSplitStr[Constants.TWO] != null) {
                tPart = tSplitStr[2];
            }

        }
        return tPart;
    }

    /**
     * 验证书链。
     *
     * @param cert 证书对象
     * @return 证书是否合法
     */
    private static boolean verifyCertificateChain(X509Certificate cert) {
        if (null == cert) {
            log.error("cert must Not null");
            return false;
        }
        if (null == middleCert) {
            log.error("middleCert must Not null");
            return false;
        }
        if (null == rootCert) {
            log.error("rootCert or cert must Not null");
            return false;
        }
        try {

            X509CertSelector selector = new X509CertSelector();
            selector.setCertificate(cert);

            Set<TrustAnchor> trustAnchors = new HashSet<TrustAnchor>();
            trustAnchors.add(new TrustAnchor(rootCert, null));
            PKIXBuilderParameters pkixParams = new PKIXBuilderParameters(
                    trustAnchors, selector);

            Set<X509Certificate> intermediateCerts = new HashSet<X509Certificate>(4);
            intermediateCerts.add(rootCert);
            intermediateCerts.add(middleCert);
            intermediateCerts.add(cert);

            pkixParams.setRevocationEnabled(false);

            CertStore intermediateCertStore = CertStore.getInstance("Collection",
                    new CollectionCertStoreParameters(intermediateCerts), "BC");
            pkixParams.addCertStore(intermediateCertStore);

            CertPathBuilder builder = CertPathBuilder.getInstance("PKIX", "BC");

            @SuppressWarnings("unused")
            PKIXCertPathBuilderResult result = (PKIXCertPathBuilderResult) builder
                    .build(pkixParams);
            log.info("verify certificate chain succeed.");
            return true;
        } catch (java.security.cert.CertPathBuilderException e) {
            log.error("verify certificate chain fail.", e);
        } catch (Exception e) {
            log.error("verify certificate chain exception: ", e);
        }
        return false;
    }

    /**
     * 检查证书链
     *
     * @param cert             待验证的证书
     * @param ifValidateCnName 是否有效名称
     * @param unionPayCnName   银联名称
     * @return 是否合法证书
     */
    public static boolean verifyCertificate(X509Certificate cert, boolean ifValidateCnName, String unionPayCnName) {
        if (null == cert) {
            log.error("cert must Not null");
            return false;
        }
        try {
            //验证有效期
            cert.checkValidity();
            if (!verifyCertificateChain(cert)) {
                return false;
            }
        } catch (Exception e) {
            log.error("verifyCertificate fail", e);
            return false;
        }

        if (ifValidateCnName) {
            // 验证公钥是否属于银联
            if (!unionPayCnName.equals(CertUtil.getIdentitiesFromCertficate(cert))) {
                log.error("cer owner is not CUP:" + CertUtil.getIdentitiesFromCertficate(cert));
                return false;
            }
        } else {
            // 验证公钥是否属于银联
            if (!unionPayCnName.equals(CertUtil.getIdentitiesFromCertficate(cert))
                    && !"00040000:SIGN".equals(CertUtil.getIdentitiesFromCertficate(cert))) {
                log.error("cer owner is not CUP:" + CertUtil.getIdentitiesFromCertficate(cert));
                return false;
            }
        }
        return true;
    }

    /**
     * 打印系统环境信息
     */
    public static void printSysInfo() {
        log.info("================= SYS INFO begin====================");
        log.info("os_name:" + System.getProperty("os.name"));
        log.info("os_arch:" + System.getProperty("os.arch"));
        log.info("os_version:" + System.getProperty("os.version"));
        log.info("java_vm_specification_name:"
                + System.getProperty("java.vm.specification.name"));
        log.info("java_vm_version:"
                + System.getProperty("java.vm.version"));
        log.info("java_vm_name:" + System.getProperty("java.vm.name"));
        log.info("java.vm.vendor=[" + System.getProperty("java.vm.vendor") + "]");
        log.info("java.version=[" + System.getProperty("java.version") + "]");
        log.info("Providers List:");
        Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            log.info(i + 1 + "." + providers[i].getName());
        }
        log.info("================= SYS INFO end=====================");
    }

    public PublicKey getEncryptCertPublicKey() {
        return encryptCert.getPublicKey();
    }


    /**
     * 根据signMethod的值，提供三种计算签名的方法
     *
     * @param data     待签名数据Map键值对形式
     * @param encoding 编码
     * @return 签名是否成功
     */
    public boolean signBool(Map<String, String> data, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = Constants.CHARSET;
        }
        String signMethod = data.get(Constants.PARAM_SIGN_METHOD);
        String version = data.get(Constants.PARAM_VERSION);
        if (!Constants.VERSION_1_0_0.equals(version) && !Constants.VERSION_5_0_1.equals(version) && StringUtils.isEmpty(signMethod)) {
            log.error("signMethod must Not null");
            return false;
        }

        if (StringUtils.isEmpty(version)) {
            log.error("version must Not null");
            return false;
        }
        if (Constants.SIGNMETHOD_RSA.equals(signMethod) || Constants.VERSION_1_0_0.equals(version) || Constants.VERSION_5_0_1.equals(version)) {
            if (Constants.VERSION_5_0_0.equals(version) || Constants.VERSION_1_0_0.equals(version) || Constants.VERSION_5_0_1.equals(version)) {
                // 设置签名证书序列号
                data.put(Constants.PARAM_CERT_ID, getSignCertId());
                // 将Map信息转换成key1=value1&key2=value2的形式
                String stringData = coverMap2String(data);
                log.info("打印排序后待签名请求报文串（交易返回11验证签名失败时可以用来同正确的进行比对）:[" + stringData + "]");
                String stringSign = null;
                try {
                    // 通过SHA1进行摘要并转16进制
                    byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
                    log.info("打印摘要（交易返回11验证签名失败可以用来同正确的进行比对）:[" + new String(signDigest) + "]");
                    stringSign = Base64Util.encode(SecureUtil.signBySoft(
                            getSignCertPrivateKey(signCertConfig.getPwd()), signDigest));
                    // 设置签名域值
                    data.put(Constants.PARAM_SIGNATURE, stringSign);
                    return true;
                } catch (Exception e) {
                    log.error("Sign Error", e);
                    return false;
                }
            } else if (Constants.VERSION_5_1_0.equals(version)) {
                // 设置签名证书序列号
                data.put(Constants.PARAM_CERT_ID, getSignCertId());
                // 将Map信息转换成key1=value1&key2=value2的形式
                String stringData = coverMap2String(data);
                log.info("打印待签名请求报文串（交易返回11验证签名失败时可以用来同正确的进行比对）:[" + stringData + "]");
                String stringSign = null;
                try {
                    // 通过SHA256进行摘要并转16进制
                    byte[] signDigest = SecureUtil.sha256X16(stringData, encoding);
                    log.info("打印摘要（交易返回11验证签名失败可以用来同正确的进行比对）:[" + new String(signDigest) + "]");
                    stringSign = Base64Util.encode(
                            SecureUtil.signBySoft256(getSignCertPrivateKey(signCertConfig.getPwd()), signDigest));
                    // 设置签名域值
                    data.put(Constants.PARAM_SIGNATURE, stringSign);
                    return true;
                } catch (Exception e) {
                    log.error("Sign Error", e);
                    return false;
                }
            }
        } else if (Constants.SIGNMETHOD_SHA256.equals(signMethod)) {
            return signBySecureKeyBool(data, signCertConfig.getPwd(), encoding);
        } else if (Constants.SIGNMETHOD_SM3.equals(signMethod)) {
            return signBySecureKeyBool(data, signCertConfig.getPwd(), encoding);
        }
        return false;
    }

    /**
     * 通过传入的证书绝对路径和证书密码读取签名证书进行签名并返回签名值<br>
     *
     * @param data      待签名数据Map键值对形式
     * @param encoding  编码
     * @param secureKey 证书密码
     * @return 签名值
     */
    public boolean signBySecureKeyBool(Map<String, String> data, String secureKey,
                                       String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = Constants.CHARSET;
        }
        if (StringUtils.isEmpty(secureKey)) {
            log.error("secureKey is empty");
            return false;
        }
        String signMethod = data.get(Constants.PARAM_SIGN_METHOD);
        if (StringUtils.isEmpty(signMethod)) {
            log.error("signMethod must Not null");
            return false;
        }

        if (Constants.SIGNMETHOD_SHA256.equals(signMethod)) {
            // 将Map信息转换成key1=value1&key2=value2的形式
            String stringData = coverMap2String(data);
            log.info("待签名请求报文串:[" + stringData + "]");
            String strBeforeSha256 = stringData
                    + Constants.AMPERSAND
                    + SecureUtil.sha256X16Str(secureKey, encoding);
            String strAfterSha256 = SecureUtil.sha256X16Str(strBeforeSha256,
                    encoding);
            // 设置签名域值
            data.put(Constants.PARAM_SIGNATURE, strAfterSha256);
            return true;
        } else if (Constants.SIGNMETHOD_SM3.equals(signMethod)) {
            String stringData = coverMap2String(data);
            log.info("待签名请求报文串:[" + stringData + "]");
            String strBeforeSm3 = stringData
                    + Constants.AMPERSAND
                    + SecureUtil.sm3X16Str(secureKey, encoding);
            String strAfterSm3 = SecureUtil.sm3X16Str(strBeforeSm3, encoding);
            // 设置签名域值
            data.put(Constants.PARAM_SIGNATURE, strAfterSm3);
            return true;
        }
        return false;
    }

    /**
     * 通过传入的签名密钥进行签名并返回签名值<br>
     *
     * @param data     待签名数据Map键值对形式
     * @param encoding 编码
     * @param certPath 证书绝对路径
     * @param certPwd  证书密码
     * @return 签名值
     */
    public boolean signByCertInfoBool(Map<String, String> data,
                                      String certPath, String certPwd, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = "UTF-8";
        }
        if (StringUtils.isEmpty(certPath) || StringUtils.isEmpty(certPwd)) {
            log.error("CertPath or CertPwd is empty");
            return false;
        }
        String signMethod = data.get(Constants.PARAM_SIGN_METHOD);
        String version = data.get(Constants.PARAM_VERSION);
        if (!Constants.VERSION_1_0_0.equals(version) && !Constants.VERSION_5_0_1.equals(version) && StringUtils.isEmpty(signMethod)) {
            log.error("signMethod must Not null");
            return false;
        }
        if (StringUtils.isEmpty(version)) {
            log.error("version must Not null");
            return false;
        }

        if (Constants.SIGNMETHOD_RSA.equals(signMethod)
                || Constants.VERSION_1_0_0.equals(version)
                || Constants.VERSION_5_0_1.equals(version)) {
            if (Constants.VERSION_5_0_0.equals(version)
                    || Constants.VERSION_1_0_0.equals(version)
                    || Constants.VERSION_5_0_1.equals(version)) {
                // 设置签名证书序列号
                data.put(Constants.PARAM_CERT_ID, CertUtil.getCertIdByKeyStoreMap(certPath, certPwd));
                // 将Map信息转换成key1=value1&key2=value2的形式
                String stringData = coverMap2String(data);
                log.info("待签名请求报文串:[" + stringData + "]");
                String stringSign;
                try {
                    // 通过SHA1进行摘要并转16进制
                    byte[] signDigest = SecureUtil
                            .sha1X16(stringData, encoding);
                    stringSign = Base64Util.encode(SecureUtil.signBySoft(
                            CertUtil.getSignCertPrivateKeyByStoreMap(certPath, certPwd), signDigest));
                    // 设置签名域值
                    data.put(Constants.PARAM_SIGNATURE, stringSign);
                    return true;
                } catch (Exception e) {
                    log.error("Sign Error", e);
                    return false;
                }
            } else if (Constants.VERSION_5_1_0.equals(version)) {
                // 设置签名证书序列号
                data.put(Constants.PARAM_CERT_ID, CertUtil.getCertIdByKeyStoreMap(certPath, certPwd));
                // 将Map信息转换成key1=value1&key2=value2的形式
                String stringData = coverMap2String(data);
                log.info("待签名请求报文串:[" + stringData + "]");
                String stringSign = null;
                try {
                    // 通过SHA256进行摘要并转16进制
                    byte[] signDigest = SecureUtil.sha256X16(stringData, encoding);
                    byte[] waitEncode = SecureUtil.signBySoft256(
                            CertUtil.getSignCertPrivateKeyByStoreMap(certPath, certPwd), signDigest);
                    stringSign = Base64Util.encode(waitEncode);
                    // 设置签名域值
                    data.put(Constants.PARAM_SIGNATURE, stringSign);
                    return true;
                } catch (Exception e) {
                    log.error("Sign Error", e);
                    return false;
                }
            }

        }
        return false;
    }

    /**
     * 验证签名
     *
     * @param resData  返回报文数据
     * @param encoding 编码格式
     * @return
     */
    public boolean validateBySecureKeyBool(Map<String, String> resData, String secureKey, String encoding) {
        log.info("验签处理开始");
        if (StringUtils.isEmpty(encoding)) {
            encoding = "UTF-8";
        }
        if (StringUtils.isEmpty(secureKey)) {
            log.error("secureKey is empty");
            return false;
        }
        String signMethod = resData.get(Constants.PARAM_SIGN_METHOD);
        if (Constants.SIGNMETHOD_SHA256.equals(signMethod)) {
            // 1.进行SHA256验证
            String stringSign = resData.get(Constants.PARAM_SIGNATURE);
            log.info("签名原文：[" + stringSign + "]");
            // 将Map信息转换成key1=value1&key2=value2的形式
            String stringData = coverMap2String(resData);
            log.info("待验签返回报文串：[" + stringData + "]");
            String strBeforeSha256 = stringData
                    + Constants.AMPERSAND
                    + SecureUtil.sha256X16Str(secureKey, encoding);
            String strAfterSha256 = SecureUtil.sha256X16Str(strBeforeSha256,
                    encoding);
            return stringSign.equals(strAfterSha256);
        } else if (Constants.SIGNMETHOD_SM3.equals(signMethod)) {
            // 1.进行SM3验证
            String stringSign = resData.get(Constants.PARAM_SIGNATURE);
            log.info("签名原文：[" + stringSign + "]");
            // 将Map信息转换成key1=value1&key2=value2的形式
            String stringData = coverMap2String(resData);
            log.info("待验签返回报文串：[" + stringData + "]");
            String strBeforeSm3 = stringData
                    + Constants.AMPERSAND
                    + SecureUtil.sm3X16Str(secureKey, encoding);
            String strAfterSm3 = SecureUtil
                    .sm3X16Str(strBeforeSm3, encoding);
            return stringSign.equals(strAfterSm3);
        }
        return false;
    }

    /**
     * 验证签名
     *
     * @param resData  返回报文数据
     * @param encoding 编码格式
     * @return
     */
    public boolean validateBool(Map<String, String> resData, String encoding) {
        log.info("验签处理开始");
        if (StringUtils.isEmpty(encoding)) {
            encoding = "UTF-8";
        }
        String signMethod = resData.get(Constants.PARAM_SIGN_METHOD);
        String version = resData.get(Constants.PARAM_VERSION);
        if (Constants.SIGNMETHOD_RSA.equals(signMethod) || Constants.VERSION_1_0_0.equals(version) || Constants.VERSION_5_0_1.equals(version)) {
            // 获取返回报文的版本号
            if (Constants.VERSION_5_0_0.equals(version) || Constants.VERSION_1_0_0.equals(version) || Constants.VERSION_5_0_1.equals(version)) {
                String stringSign = resData.get(Constants.PARAM_SIGNATURE);
                log.info("签名原文：[" + stringSign + "]");
                // 从返回报文中获取certId ，然后去证书静态Map中查询对应验签证书对象
                String certId = resData.get(Constants.PARAM_CERT_ID);
                log.info("对返回报文串验签使用的验签公钥序列号：[" + certId + "]");
                // 将Map信息转换成key1=value1&key2=value2的形式
                String stringData = coverMap2String(resData);
                log.info("待验签返回报文串：[" + stringData + "]");
                try {
                    // 验证签名需要用银联发给商户的公钥证书.
                    return SecureUtil.validateSignBySoft(getValidatePublicKey(certId, unionPayConfig.getSignMethod(), unionPayConfig.getCertDir()), Base64Util.decode(stringSign),
                            SecureUtil.sha1X16(stringData, encoding));
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage(), e);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            } else if (Constants.VERSION_5_1_0.equals(version)) {
                // 1.从返回报文中获取公钥信息转换成公钥对象
                String strCert = resData.get(Constants.PARAM_SIGN_PUB_KEY_CERT);
                X509Certificate x509Cert = CertUtil.genCertificateByStr(strCert);
                if (x509Cert == null) {
                    log.error("convert signPubKeyCert failed");
                    return false;
                }
                // 2.验证证书链
                if (!CertUtil.verifyCertificate(x509Cert, unionPayConfig.getIfValidateCnName(), Constants.UNIONPAY_CNNAME)) {
                    log.error("验证公钥证书失败，证书信息：[" + strCert + "]");
                    return false;
                }

                // 3.验签
                String stringSign = resData.get(Constants.PARAM_SIGNATURE);
                log.info("签名原文：[" + stringSign + "]");
                // 将Map信息转换成key1=value1&key2=value2的形式
                String stringData = coverMap2String(resData);
                log.info("待验签返回报文串：[" + stringData + "]");
                try {
                    // 验证签名需要用银联发给商户的公钥证书.
                    boolean result = SecureUtil.validateSignBySoft256(x509Cert
                            .getPublicKey(), Base64Util.decode(stringSign), SecureUtil.sha256X16(
                            stringData, encoding));
                    log.info("验证签名" + (result ? "成功" : "失败"));
                    return result;
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage(), e);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }

        } else if (Constants.SIGNMETHOD_SHA256.equals(signMethod)) {
            return validateBySecureKey(resData, signCertConfig.getPwd(), encoding);
        } else if (Constants.SIGNMETHOD_SM3.equals(signMethod)) {
            return validateBySecureKey(resData, signCertConfig.getPwd(), encoding);
        }
        return false;
    }

    /**
     * 将Map中的数据转换成key1=value1&key2=value2的形式 不包含签名域signature
     *
     * @param data 待拼接的Map数据
     * @return 拼接好后的字符串
     */
    public static String coverMap2String(Map<String, String> data) {
        TreeMap<String, String> tree = new TreeMap<String, String>();
        Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> en = it.next();
            if (Constants.PARAM_SIGNATURE.equals(en.getKey().trim())) {
                continue;
            }
            tree.put(en.getKey(), en.getValue());
        }
        it = tree.entrySet().iterator();
        StringBuffer sf = new StringBuffer();
        while (it.hasNext()) {
            Map.Entry<String, String> en = it.next();
            sf.append(en.getKey() + Constants.EQUAL + en.getValue()
                    + Constants.AMPERSAND);
        }
        return sf.substring(0, sf.length() - 1);
    }

    /**
     * 将形如key=value&key=value的字符串转换为相应的Map对象
     *
     * @param result
     * @return
     */
    public static Map<String, String> convertResultStringToMap(String result) {
        Map<String, String> map = null;

        if (result != null && !StringUtils.isEmpty(result.trim())) {
            if (result.startsWith(Constants.LEFT_BRACE) && result.endsWith(Constants.RIGHT_BRACE)) {
                result = result.substring(1, result.length() - 1);
            }
            map = parseQstring(result);
        }
        return map;
    }


    /**
     * 解析应答字符串，生成应答要素
     *
     * @param str 需要解析的字符串
     * @return 解析的结果map
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> parseQstring(String str) {

        Map<String, String> map = new HashMap<>(16);
        int len = str.length();
        StringBuilder temp = new StringBuilder();
        char curChar;
        String key = null;
        boolean isKey = true;
        //值里有嵌套
        boolean isOpen = false;
        char openName = 0;
        if (len > 0) {
            // 遍历整个带解析的字符串
            for (int i = 0; i < len; i++) {
                // 取当前字符
                curChar = str.charAt(i);
                // 如果当前生成的是key
                if (isKey) {
                    // 如果读取到=分隔符
                    if (curChar == '=') {
                        key = temp.toString();
                        temp.setLength(0);
                        isKey = false;
                    } else {
                        temp.append(curChar);
                    }
                }
                // 如果当前生成的是value
                else {
                    if (isOpen) {
                        if (curChar == openName) {
                            isOpen = false;
                        }

                    }
                    //如果没开启嵌套
                    else {
                        //如果碰到，就开启嵌套
                        if (curChar == '{') {
                            isOpen = true;
                            openName = '}';
                        }
                        if (curChar == '[') {
                            isOpen = true;
                            openName = ']';
                        }
                    }
                    // 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
                    if (curChar == '&' && !isOpen) {
                        putKeyValueToMap(temp, isKey, key, map);
                        temp.setLength(0);
                        isKey = true;
                    } else {
                        temp.append(curChar);
                    }
                }

            }
            putKeyValueToMap(temp, isKey, key, map);
        }
        return map;
    }

    private static void putKeyValueToMap(StringBuilder temp, boolean isKey,
                                         String key, Map<String, String> map) {
        if (isKey) {
            key = temp.toString();
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, "");
        } else {
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, temp.toString());
        }
    }

    /**
     * 获取应答报文中的加密公钥证书,并存储到本地,并备份原始证书<br>
     * 更新成功则返回1，无更新返回0，失败异常返回-1。
     *
     * @param resData
     * @param encoding
     * @return
     */
    public int getEncryptCert(Map<String, String> resData,
                              String encoding) {
        String strCert = resData.get(Constants.PARAM_ENCRYPT_PUB_KEY_CERT);
        String certType = resData.get(Constants.PARAM_CERT_TYPE);
        if (StringUtils.isEmpty(strCert) || StringUtils.isEmpty(certType)) {
            return -1;
        }
        X509Certificate x509Cert = CertUtil.genCertificateByStr(strCert);
        if (Constants.CERTTYPE_01.equals(certType)) {
            // 更新敏感信息加密公钥
            if (!getSignCertId().equals(
                    x509Cert.getSerialNumber().toString())) {
                // ID不同时进行本地证书更新操作
                String localCertPath = unionPayConfig.getEncryptCertPath();
                String newLocalCertPath = genBackupName(localCertPath);
                // 1.将本地证书进行备份存储
                if (!copyFile(localCertPath, newLocalCertPath)) {
                    return -1;
                }

                // 2.备份成功,进行新证书的存储
                if (!writeFile(localCertPath, strCert, encoding)) {
                    return -1;
                }
                log.info("save new encryptPubKeyCert success");
                resetEncryptCertPublicKey();
                return 1;
            } else {
                return 0;
            }
        } else if (Constants.CERTTYPE_02.equals(certType)) {
            return 0;
        } else {
            log.info("unknown cerType:" + certType);
            return -1;
        }
    }

    /**
     * 文件拷贝方法
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @return
     * @throws IOException
     */
    public static boolean copyFile(String srcFile, String destFile) {
        boolean flag = false;
        FileInputStream fin = null;
        FileOutputStream fout = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            // 获取源文件和目标文件的输入输出流
            fin = new FileInputStream(srcFile);
            fout = new FileOutputStream(destFile);
            // 获取输入输出通道
            fcin = fin.getChannel();
            fcout = fout.getChannel();
            // 创建缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                // clear方法重设缓冲区，使它可以接受读入的数据
                buffer.clear();
                // 从输入通道中将数据读到缓冲区
                int r = fcin.read(buffer);
                // read方法返回读取的字节数，可能为零，如果该通道已到达流的末尾，则返回-1
                if (r == -1) {
                    flag = true;
                    break;
                }
                // flip方法让缓冲区可以将新读入的数据写入另一个通道
                buffer.flip();
                // 从输出通道中将数据写入缓冲区
                fcout.write(buffer);
            }
            fout.flush();
        } catch (IOException e) {
            log.error("CopyFile fail", e);
        } finally {
            try {
                if (null != fin) {
                    fin.close();
                }
                if (null != fout) {
                    fout.close();
                }
                if (null != fcin) {
                    fcin.close();
                }
                if (null != fcout) {
                    fcout.close();
                }
            } catch (IOException ex) {
                log.error("Releases any system resources fail", ex);
            }
        }
        return flag;
    }

    /**
     * 写文件方法
     *
     * @param filePath    文件路径
     * @param fileContent 文件内容
     * @param encoding    编码
     * @return
     */
    public static boolean writeFile(String filePath, String fileContent,
                                    String encoding) {
        FileOutputStream fout = null;
        FileChannel fcout = null;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            fout = new FileOutputStream(filePath);
            // 获取输出通道
            fcout = fout.getChannel();
            // 创建缓冲区
            ByteBuffer buffer = ByteBuffer.wrap(fileContent.getBytes(encoding));
            fcout.write(buffer);
            fout.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (null != fout) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fcout) {
                try {
                    fcout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 将传入的文件名(xxx)改名 <br>
     * 结果为： xxx_backup.cer
     *
     * @param fileName 文件名称
     * @return 备份文件名称
     */
    public static String genBackupName(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }

        int i = fileName.lastIndexOf(Constants.POINT);
        String leftFileName = fileName.substring(0, i);
        String rightFileName = fileName.substring(i + 1);
        String newFileName = leftFileName + "_backup" + Constants.POINT + rightFileName;
        return newFileName;
    }


    public static byte[] readFileByNio(String filePath) {
        FileInputStream in = null;
        FileChannel fc = null;
        ByteBuffer bf = null;
        try {
            in = new FileInputStream(filePath);
            fc = in.getChannel();
            bf = ByteBuffer.allocate((int) fc.size());
            fc.read(bf);
            return bf.array();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 过滤请求报文中的空字符串或者空字符串
     *
     * @param contentData 内容数据
     * @return 过滤空格后的内容
     */
    public static Map<String, String> filterBlank(Map<String, String> contentData) {
        log.info("打印请求报文域 :");
        Map<String, String> submitFromData = new HashMap<String, String>();
        Set<String> keyset = contentData.keySet();

        for (String key : keyset) {
            String value = contentData.get(key);
            if (value != null && !"".equals(value.trim())) {
                // 对value值进行去除前后空处理
                submitFromData.put(key, value.trim());
                log.info(key + "-->" + String.valueOf(value));
            }
        }
        return submitFromData;
    }

    /**
     * 解压缩.
     *
     * @param inputByte byte[]数组类型的数据
     * @return 解压缩后的数据
     * @throws IOException
     */
    public static byte[] inflater(final byte[] inputByte) throws IOException {
        int compressedDataLength = 0;
        Inflater compresser = new Inflater(false);
        compresser.setInput(inputByte, 0, inputByte.length);
        ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
        byte[] result = new byte[1024];
        try {
            while (!compresser.finished()) {
                compressedDataLength = compresser.inflate(result);
                if (compressedDataLength == 0) {
                    break;
                }
                o.write(result, 0, compressedDataLength);
            }
        } catch (Exception ex) {
            System.err.println("Data format error!\n");
            ex.printStackTrace();
        } finally {
            o.close();
        }
        compresser.end();
        return o.toByteArray();
    }

    /**
     * 压缩.
     *
     * @param inputByte 需要解压缩的byte[]数组
     * @return 压缩后的数据
     * @throws IOException
     */
    public static byte[] deflater(final byte[] inputByte) throws IOException {
        int compressedDataLength = 0;
        Deflater compresser = new Deflater();
        compresser.setInput(inputByte);
        compresser.finish();
        ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
        byte[] result = new byte[1024];
        try {
            while (!compresser.finished()) {
                compressedDataLength = compresser.deflate(result);
                o.write(result, 0, compressedDataLength);
            }
        } finally {
            o.close();
        }
        compresser.end();
        return o.toByteArray();
    }


    /**
     * 请求报文签名(使用配置文件中配置的私钥证书或者对称密钥签名)<br>
     * 功能：对请求报文进行签名,并计算赋值certid,signature字段并返回<br>
     *
     * @param reqData  请求报文map<br>
     * @param encoding 上送请求报文域encoding字段的值<br>
     * @return　签名后的map对象<br>
     */
    public Map<String, String> sign(Map<String, String> reqData, String encoding) {
        signBool(reqData, encoding);
        return reqData;
    }

    /**
     * 同signByCertInfo<br>
     *
     * @param reqData
     * @param certPath
     * @param certPwd
     * @param encoding
     * @return
     */
    public Map<String, String> sign(Map<String, String> reqData, String certPath,
                                    String certPwd, String encoding) {
        signByCertInfo(reqData, certPath, certPwd, encoding);
        return reqData;
    }

    /**
     * 多证书签名(通过传入私钥证书路径和密码签名）<br>
     * 功能：如果有多个商户号接入银联,每个商户号对应不同的证书可以使用此方法:传入私钥证书和密码(并且在acp_sdk.properties中 配置 acpsdk.singleMode=false)<br>
     *
     * @param reqData  请求报文map<br>
     * @param certPath 签名私钥文件（带路径）<br>
     * @param certPwd  签名私钥密码<br>
     * @param encoding 上送请求报文域encoding字段的值<br>
     * @return　签名后的map对象<br>
     */
    public Map<String, String> signByCertInfo(Map<String, String> reqData, String certPath,
                                              String certPwd, String encoding) {
        signByCertInfo(reqData, certPath, certPwd, encoding);
        return reqData;
    }

    /**
     * 多密钥签名(通过传入密钥签名)<br>
     * 功能：如果有多个商户号接入银联,每个商户号对应不同的证书可以使用此方法:传入私钥证书和密码(并且在acp_sdk.properties中 配置 acpsdk.singleMode=false)<br>
     *
     * @param reqData   请求报文map<br>
     * @param secureKey 签名对称密钥<br>
     * @param encoding  上送请求报文域encoding字段的值<br>
     * @return　签名后的map对象<br>
     */
    public Map<String, String> signBySecureKey(Map<String, String> reqData, String secureKey, String encoding) {
        signBySecureKey(reqData, secureKey, encoding);
        return reqData;
    }

    /**
     * 验证签名(SHA-1摘要算法)<br>
     *
     * @param rspData  返回报文数据<br>
     * @param encoding 上送请求报文域encoding字段的值<br>
     * @return true 通过 false 未通过<br>
     */
    public boolean validate(Map<String, String> rspData, String encoding) {
        return validate(rspData, encoding);
    }

    /**
     * 多密钥验签(通过传入密钥签名)<br>
     *
     * @param rspData   返回报文数据<br>
     * @param encoding  上送请求报文域encoding字段的值<br>
     * @param secureKey
     * @return true 通过 false 未通过<br>
     */
    public boolean validateBySecureKey(Map<String, String> rspData, String secureKey, String encoding) {
        return validateBySecureKey(rspData, secureKey, encoding);
    }

    /**
     * @param jsonData json格式数据
     * @return 是否成功
     * @deprecated 5.1.0开发包已删除此方法，请直接参考5.1.0开发包中的VerifyAppData.java验签。
     * 对控件支付成功返回的结果信息中data域进行验签（控件端获取的应答信息）<br>
     */
    public boolean validateAppResponse(String jsonData, String encoding) {
        log.info("控件应答信息验签处理开始：[" + jsonData + "]");
        if (StringUtils.isEmpty(encoding)) {
            encoding = Constants.CHARSET;
        }
        Matcher m = signPattern.matcher(jsonData);
        if (!m.find()) {
            return false;
        }
        String sign = m.group(1);
        m = dataPattern.matcher(jsonData);
        if (!m.find()) {
            return false;
        }
        String data = m.group(1);

        m = certPattern.matcher(jsonData);
        if (!m.find()) {
            return false;
        }
        try {
            // 验证签名需要用银联发给商户的公钥证书.
            return SecureUtil.validateSignBySoft(
                    getEncryptCertPublicKey(),
                    Base64Util.decode(sign),
                    SecureUtil.sha1X16(data, encoding));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 功能：前台交易构造HTTP POST自动提交表单<br>
     *
     * @param reqUrl   表单提交地址<br>
     * @param hiddens  以MAP形式存储的表单键值<br>
     * @param encoding 上送请求报文域encoding字段的值<br>
     * @return 构造好的HTTP POST交易表单<br>
     */
    public static String createAutoFormHtml(String reqUrl, Map<String, String> hiddens, String encoding) {
        StringBuffer sf = new StringBuffer();
        sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + encoding + "\"/></head><body>");
        sf.append("<form id = \"pay_form\" action=\"" + reqUrl
                + "\" method=\"post\">");
        if (null != hiddens && 0 != hiddens.size()) {
            Set<Map.Entry<String, String>> set = hiddens.entrySet();
            Iterator<Map.Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> ey = it.next();
                String key = ey.getKey();
                String value = ey.getValue();
                sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
                        + key + "\" value=\"" + value + "\"/>");
            }
        }
        sf.append("</form>");
        sf.append("</body>");
        sf.append("<script type=\"text/javascript\">");
        sf.append("document.all.pay_form.submit();");
        sf.append("</script>");
        sf.append("</html>");
        return sf.toString();
    }


    /**
     * 功能：将批量文件内容使用DEFLATE压缩算法压缩，Base64编码生成字符串并返回<br>
     * 适用到的交易：批量代付，批量代收，批量退货<br>
     *
     * @param filePath 批量文件-全路径文件名<br>
     * @return
     */
    public static String enCodeFileContent(String filePath, String encoding) {
        String baseFileContent = "";

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            int fl = in.available();
            if (null != in) {
                byte[] s = new byte[fl];
                in.read(s, 0, fl);
                // 压缩编码.
                baseFileContent = Base64Util.encode(CertUtil.deflater(s));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return baseFileContent;
    }

    /**
     * 功能：解析交易返回的fileContent字符串并落地 （ 解base64，解DEFLATE压缩并落地）<br>
     * 适用到的交易：对账文件下载，批量交易状态查询<br>
     *
     * @param resData       返回报文map<br>
     * @param fileDirectory 落地的文件目录（绝对路径）
     * @param encoding      上送请求报文域encoding字段的值<br>
     */
    public static String deCodeFileContent(Map<String, String> resData, String fileDirectory, String encoding) {
        // 解析返回文件
        String filePath = null;
        String fileContent = resData.get(Constants.PARAM_FILE_CONTENT);
        if (null != fileContent && !"".equals(fileContent)) {
            FileOutputStream out = null;
            try {
                byte[] fileArray = CertUtil.inflater(Base64Util.decode(fileContent));
                if (StringUtils.isEmpty(resData.get("fileName"))) {
                    filePath = fileDirectory + File.separator + resData.get("merId")
                            + "_" + resData.get("batchNo") + "_"
                            + resData.get("txnTime") + ".txt";
                } else {
                    filePath = fileDirectory + File.separator + resData.get("fileName");
                }
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                out = new FileOutputStream(file);
                out.write(fileArray, 0, fileArray.length);
                out.flush();
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }

    /**
     * 功能：将结果文件内容 转换成明文字符串：解base64,解压缩<br>
     * 适用到的交易：批量交易状态查询<br>
     *
     * @param fileContent 批量交易状态查询返回的文件内容<br>
     * @return 内容明文<br>
     */
    public static String getFileContent(String fileContent, String encoding) {
        String fc = "";
        try {
            fc = new String(CertUtil.inflater(Base64Util.decode(fileContent)), encoding);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return fc;
    }


    /**
     * 功能：持卡人信息域customerInfo构造<br>
     * 说明：不勾选对敏感信息加密权限使用旧的构造customerInfo域方式，不对敏感信息进行加密（对 phoneNo，cvn2， expired不加密），但如果送pin的话则加密<br>
     *
     * @param customerInfoMap 信息域请求参数 key送域名value送值,必送<br>
     *                        例如：customerInfoMap.put("certifTp", "01");					//证件类型<br>
     *                        customerInfoMap.put("certifId", "341126197709218366");	//证件号码<br>
     *                        customerInfoMap.put("customerNm", "互联网");				//姓名<br>
     *                        customerInfoMap.put("phoneNo", "13552535506");			//手机号<br>
     *                        customerInfoMap.put("smsCode", "123456");					//短信验证码<br>
     *                        customerInfoMap.put("pin", "111111");						//密码（加密）<br>
     *                        customerInfoMap.put("cvn2", "123");           			//卡背面的cvn2三位数字（不加密）<br>
     *                        customerInfoMap.put("expired", "2311");  				    //有效期 年在前月在后（不加密)<br>
     * @param accNo           customerInfoMap送了密码那么卡号必送,如果customerInfoMap未送密码pin，此字段可以不送<br>
     * @param encoding        上送请求报文域encoding字段的值<br>
     * @return base64后的持卡人信息域字段<br>
     */
    public String getCustomerInfo(Map<String, String> customerInfoMap, String accNo, String encoding) {

        if (customerInfoMap.isEmpty()) {
            return "{}";
        }
        StringBuffer sf = new StringBuffer("{");
        for (Iterator<String> it = customerInfoMap.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            String value = customerInfoMap.get(key);
            if ("pin".equals(key)) {
                if (null == accNo || "".equals(accNo.trim())) {
                    log.info("送了密码（PIN），必须在getCustomerInfo参数中上传卡号");
                    throw new RuntimeException("加密PIN没送卡号无法后续处理");
                } else {
                    value = encryptPin(accNo, value);
                }
            }
            sf.append(key).append(Constants.EQUAL).append(value);
            if (it.hasNext()) {
                sf.append(Constants.AMPERSAND);
            }
        }
        String customerInfo = sf.append("}").toString();
        log.info("组装的customerInfo明文：" + customerInfo);
        return Base64Util.encode(customerInfo.getBytes());
    }

    /**
     * 功能：持卡人信息域customerInfo构造，勾选对敏感信息加密权限 适用新加密规范，对pin和phoneNo，cvn2，expired加密 <br>
     * 适用到的交易： <br>
     *
     * @param customerInfoMap 信息域请求参数 key送域名value送值,必送 <br>
     *                        例如：customerInfoMap.put("certifTp", "01");					//证件类型 <br>
     *                        customerInfoMap.put("certifId", "341126197709218366");	//证件号码 <br>
     *                        customerInfoMap.put("customerNm", "互联网");				//姓名 <br>
     *                        customerInfoMap.put("smsCode", "123456");					//短信验证码 <br>
     *                        customerInfoMap.put("pin", "111111");						//密码（加密） <br>
     *                        customerInfoMap.put("phoneNo", "13552535506");			//手机号（加密） <br>
     *                        customerInfoMap.put("cvn2", "123");           			//卡背面的cvn2三位数字（加密） <br>
     *                        customerInfoMap.put("expired", "2311");  				    //有效期 年在前月在后（加密） <br>
     * @param accNo           customerInfoMap送了密码那么卡号必送,如果customerInfoMap未送密码PIN，此字段可以不送<br>
     * @param encoding        上送请求报文域encoding字段的值
     * @return base64后的持卡人信息域字段 <br>
     */
    public String getCustomerInfoWithEncrypt(Map<String, String> customerInfoMap, String accNo, String encoding) {
        if (customerInfoMap.isEmpty()) {
            return "{}";
        }
        StringBuffer sf = new StringBuffer("{");
        //敏感信息加密域
        StringBuffer encryptedInfoSb = new StringBuffer("");

        for (Iterator<String> it = customerInfoMap.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            String value = customerInfoMap.get(key);
            if ("phoneNo".equals(key) || "cvn2".equals(key) || "expired".equals(key)) {
                encryptedInfoSb.append(key).append(Constants.EQUAL).append(value).append(Constants.AMPERSAND);
            } else {
                if ("pin".equals(key)) {
                    if (null == accNo || "".equals(accNo.trim())) {
                        log.info("送了密码（PIN），必须在getCustomerInfoWithEncrypt参数中上传卡号");
                        throw new RuntimeException("加密PIN没送卡号无法后续处理");
                    } else {
                        value = encryptPin(accNo, value);
                    }
                }
                sf.append(key).append(Constants.EQUAL).append(value).append(Constants.AMPERSAND);
            }
        }

        if (!StringUtils.isEmpty(encryptedInfoSb.toString())) {
            encryptedInfoSb.setLength(encryptedInfoSb.length() - 1);//去掉最后一个&符号
            log.info("组装的customerInfo encryptedInfo明文：" + encryptedInfoSb.toString());
            sf.append("encryptedInfo").append(Constants.EQUAL).append(encryptData(encryptedInfoSb.toString(), encoding));
        } else {
            sf.setLength(sf.length() - 1);
        }
        String customerInfo = sf.append("}").toString();
        log.info("组装的customerInfo明文：" + customerInfo);
        return Base64Util.encode(customerInfo.getBytes());
    }

    /**
     * 解析返回报文（后台通知）中的customerInfo域：<br>
     * 解base64,如果带敏感信息加密 encryptedInfo 则将其解密并将 encryptedInfo中的域放到customerInfoMap返回<br>
     *
     * @param customerInfo 客户信息<br>
     * @param encoding     编码格式<br>
     * @return
     */
    public Map<String, String> parseCustomerInfo(String customerInfo, String encoding) {
        Map<String, String> customerInfoMap = null;
        try {
            byte[] b = Base64Util.decode(customerInfo);
            String customerInfoNoBase64 = new String(b, encoding);
            log.info("解base64后===>" + customerInfoNoBase64);
            //去掉前后的{}
            customerInfoNoBase64 = customerInfoNoBase64.substring(1, customerInfoNoBase64.length() - 1);
            customerInfoMap = CertUtil.parseQstring(customerInfoNoBase64);
            if (customerInfoMap.containsKey("encryptedInfo")) {
                String encInfoStr = customerInfoMap.get("encryptedInfo");
                customerInfoMap.remove("encryptedInfo");
                String encryptedInfoStr = decryptData(encInfoStr, encoding);
                Map<String, String> encryptedInfoMap = CertUtil.parseQstring(encryptedInfoStr);
                customerInfoMap.putAll(encryptedInfoMap);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return customerInfoMap;
    }

    /**
     * 解析返回报文（后台通知）中的customerInfo域：<br>
     * 解base64,如果带敏感信息加密 encryptedInfo 则将其解密并将 encryptedInfo中的域放到customerInfoMap返回<br>
     *
     * @param customerInfo<br>
     * @param encoding<br>
     * @return
     */
    public static Map<String, String> parseCustomerInfo(String customerInfo, String certPath,
                                                        String certPwd, String encoding) {
        Map<String, String> customerInfoMap = null;
        try {
            byte[] b = Base64Util.decode(customerInfo);
            String customerInfoNoBase64 = new String(b, encoding);
            log.info("解base64后===>" + customerInfoNoBase64);
            //去掉前后的{}
            customerInfoNoBase64 = customerInfoNoBase64.substring(1, customerInfoNoBase64.length() - 1);
            customerInfoMap = CertUtil.parseQstring(customerInfoNoBase64);
            if (customerInfoMap.containsKey("encryptedInfo")) {
                String encInfoStr = customerInfoMap.get("encryptedInfo");
                customerInfoMap.remove("encryptedInfo");
                String encryptedInfoStr = decryptData(encInfoStr, certPath, certPwd, encoding);
                Map<String, String> encryptedInfoMap = CertUtil.parseQstring(encryptedInfoStr);
                customerInfoMap.putAll(encryptedInfoMap);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return customerInfoMap;
    }

    /**
     * 密码加密并做base64<br>
     *
     * @param accNo 卡号<br>
     * @param pwd   密码<br>
     * @return 加密的内容<br>
     */
    public String encryptPin(String accNo, String pwd) {
        return SecureUtil.encryptPin(accNo, pwd, getEncryptCertPublicKey());
    }

    /**
     * 敏感信息加密并做base64(卡号，手机号，cvn2,有效期）<br>
     *
     * @param data         送 phoneNo,cvn2,有效期<br>
     * @param encoding<br>
     * @return 加密的密文<br>
     */
    public String encryptData(String data, String encoding) {
        return SecureUtil.encryptData(data, encoding, getEncryptCertPublicKey());
    }

    /**
     * 敏感信息解密，使用配置文件acp_sdk.properties解密<br>
     *
     * @param base64EncryptedInfo 加密信息<br>
     * @param encoding<br>
     * @return 解密后的明文<br>
     */
    public String decryptData(String base64EncryptedInfo, String encoding) {
        return SecureUtil.decryptData(base64EncryptedInfo, encoding, getSignCertPrivateKey(signCertConfig.getPwd()));
    }

    /**
     * 敏感信息解密,通过传入的私钥解密<br>
     *
     * @param base64EncryptedInfo 加密信息<br>
     * @param certPath            私钥文件（带全路径）<br>
     * @param certPwd             私钥密码<br>
     * @param encoding<br>
     * @return
     */
    public static String decryptData(String base64EncryptedInfo, String certPath,
                                     String certPwd, String encoding) {
        return SecureUtil.decryptData(base64EncryptedInfo, encoding, CertUtil
                .getSignCertPrivateKeyByStoreMap(certPath, certPwd));
    }

    /**
     * 有卡交易信息域(cardTransData)构造<br>
     * 所有子域需用“{}”包含，子域间以“&”符号链接。格式如下：{子域名1=值&子域名2=值&子域名3=值}<br>
     * 说明：本示例仅供参考，开发时请根据接口文档中的报文要素组装<br>
     *
     * @param cardTransDataMap cardTransData的数据<br>
     * @param requestData      必须包含merId、orderId、txnTime、txnAmt，磁道加密时需要使用<br>
     * @param encoding         编码<br>
     * @return
     */
    public String getCardTransData(Map<String, String> cardTransDataMap,
                                   Map<String, String> requestData,
                                   String encoding) {

        StringBuffer cardTransDataBuffer = new StringBuffer();
        if (cardTransDataMap.containsKey("track2Data")) {
            StringBuffer track2Buffer = new StringBuffer();
            track2Buffer.append(requestData.get("merId"))
                    .append(Constants.COLON).append(requestData.get("orderId"))
                    .append(Constants.COLON).append(requestData.get("txnTime"))
                    .append(Constants.COLON).append(requestData.get("txnAmt") == null ? 0 : requestData.get("txnAmt"))
                    .append(Constants.COLON).append(cardTransDataMap.get("track2Data"));
            cardTransDataMap.put("track2Data",
                    encryptData(track2Buffer.toString(), encoding));
        }
        if (cardTransDataMap.containsKey("track3Data")) {
            StringBuffer track3Buffer = new StringBuffer();
            track3Buffer.append(requestData.get("merId"))
                    .append(Constants.COLON).append(requestData.get("orderId"))
                    .append(Constants.COLON).append(requestData.get("txnTime"))
                    .append(Constants.COLON).append(requestData.get("txnAmt") == null ? 0 : requestData.get("txnAmt"))
                    .append(Constants.COLON).append(cardTransDataMap.get("track3Data"));
            cardTransDataMap.put("track3Data",
                    encryptData(track3Buffer.toString(), encoding));
        }
        return cardTransDataBuffer.append(Constants.LEFT_BRACE)
                .append(CertUtil.coverMap2String(cardTransDataMap))
                .append(Constants.RIGHT_BRACE).toString();

    }

    /**
     * 获取应答报文中的加密公钥证书,并存储到本地,备份原始证书,并自动替换证书<br>
     * 更新成功则返回1，无更新返回0，失败异常返回-1<br>
     *
     * @param resData  返回报文
     * @param encoding
     * @return
     */
    public int updateEncryptCert(Map<String, String> resData, String encoding) {
        return getEncryptCert(resData, encoding);
    }

    /**
     * 获取
     *
     * @param number
     * @return
     */
    public int genLuhn(String number) {
        return SecureUtil.genLuhn(number);
    }
}
