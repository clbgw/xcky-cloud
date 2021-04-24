package com.xcky.util;

import com.xcky.enums.WxPaySignTypeEnum;
import com.xcky.util.encry.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 微信支付工具类
 *
 * @author lbchen
 */
@Slf4j
public class WxPayUtil {
    
    /**
     * XML格式字符串转换为Map
     *
     * @param xmlStr XML字符串
     * @return XML数据转换后的Map
     */
    public static Map<String, String> xmlToMap(String xmlStr) {
        Map<String, String> data = new HashMap<>(16);
        try {
            NodeList nodeList = getNodeListByXmlStr(xmlStr);
            if (null == nodeList) {
                return data;
            }
            int nodeListLength = nodeList.getLength();
            for (int idx = 0; idx < nodeListLength; ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            return data;
        } catch (Exception ex) {
            log.warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), xmlStr);
            ex.printStackTrace();
        }
        return data;
    }
    
    /**
     * 通过xml字符串内容获得节点列表
     *
     * @param xmlStr xml字符串内容
     * @return 节点列表
     */
    private static NodeList getNodeListByXmlStr(String xmlStr) {
        DocumentBuilder documentBuilder = newDocumentBuilder();
        InputStream stream;
        try {
            stream = new ByteArrayInputStream(xmlStr.getBytes(Constants.CHARSET));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            stream.close();
            return doc.getDocumentElement().getChildNodes();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     */
    public static String mapToXml(Map<String, String> data) {
        org.w3c.dom.Document document = newDocument();
        org.w3c.dom.Element root = document.createElement(Constants.XML);
        document.appendChild(root);
        for (String key : data.keySet()) {
            String value = String.valueOf(data.get(key));
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, Constants.CHARSET);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String output = writer.getBuffer().toString();
            writer.close();
            return output;
        } catch (TransformerException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data Map类型数据
     * @param key  API密钥
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key) {
        return generateSignedXml(data, key, WxPaySignTypeEnum.MD5);
    }
    
    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data     Map类型数据
     * @param key      API密钥
     * @param signType 签名类型
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key, WxPaySignTypeEnum signType) {
        String sign = generateSignature(data, key, signType);
        data.put(WxPayConstants.FIELD_SIGN, sign);
        return mapToXml(data);
    }
    
    
    /**
     * 判断签名是否正确
     *
     * @param xmlStr XML格式数据
     * @param key    API密钥
     * @return 签名是否正确
     */
    public static boolean isSignatureValid(String xmlStr, String key) {
        Map<String, String> data = xmlToMap(xmlStr);
        if (!data.containsKey(WxPayConstants.FIELD_SIGN)) {
            return false;
        }
        String sign = data.get(WxPayConstants.FIELD_SIGN);
        return generateSignature(data, key).equals(sign);
    }
    
    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
     *
     * @param data Map类型数据
     * @param key  API密钥
     * @return 签名是否正确
     */
    public static boolean isSignatureValid(Map<String, String> data, String key) {
        return isSignatureValid(data, key, WxPaySignTypeEnum.MD5);
    }
    
    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data     Map类型数据
     * @param key      API密钥
     * @param signType 签名方式
     * @return 签名是否正确
     */
    public static boolean isSignatureValid(Map<String, String> data, String key, WxPaySignTypeEnum signType) {
        if (!data.containsKey(WxPayConstants.FIELD_SIGN)) {
            return false;
        }
        String sign = data.get(WxPayConstants.FIELD_SIGN);
        return generateSignature(data, key, signType).equals(sign);
    }
    
    /**
     * 生成签名
     *
     * @param data 待签名数据
     * @param key  API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) {
        return generateSignature(data, key, WxPaySignTypeEnum.MD5);
    }
    
    /**
     * 生成签名<br>
     * 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data     待签名数据
     * @param key      API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key, WxPaySignTypeEnum signType) {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            // 签名属性不参与签名
            if (k.equals(WxPayConstants.FIELD_SIGN)) {
                continue;
            }
            // 参数值为空，则不参与签名
            String value = String.valueOf(data.get(k));
            if (value.trim().length() > 0) {
                sb.append(k).append("=").append(value.trim()).append("&");
            }
        }
        sb.append("key=").append(key);
        if (WxPaySignTypeEnum.MD5.equals(signType)) {
            return Md5Util.encode(sb.toString());
        } else if (WxPaySignTypeEnum.HMACSHA256.equals(signType)) {
            return hmacSha256(sb.toString(), key);
        } else {
            log.error("Invalid sign_type: " + signType.name());
        }
        return "";
    }
    
    /**
     * 生成 hmacSha256 加密后的字符串
     *
     * @param data 待处理数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String hmacSha256(String data, String key) {
        Mac sha256Hmac;
        try {
            sha256Hmac = Mac.getInstance(Constants.HMAC_SHA_256);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(Constants.CHARSET), Constants.HMAC_SHA_256);
            sha256Hmac.init(secretKey);
            byte[] array = sha256Hmac.doFinal(data.getBytes(Constants.CHARSET));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    private static DocumentBuilder newDocumentBuilder() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);
            return documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static Document newDocument() {
        return newDocumentBuilder().newDocument();
    }
}
