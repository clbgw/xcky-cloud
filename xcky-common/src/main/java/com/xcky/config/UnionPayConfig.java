package com.xcky.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 银联云闪付配置
 *
 * @author lbchen
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "unionpay")
public class UnionPayConfig {

    private String mchId = "777290058184213";

    private String certDir = "";

    private String encryptTrackKeyModulus;

    private String encryptTrackKeyExponent;

    private UnionPaySignCertConfig signcert;

    private String version = "5.1.0";

    private String signMethod = "01";

    private Boolean ifValidateCnName = true;

    private Boolean ifValidateRemoteCert = true;

    private String backUrl;

    private String frontUrl;

    private String encryptCertPath;
    private String middleCertPath;
    private String rootCertPath;

    /**
     * 交易产品
     */
    private String frontTransUrl;
    private String backTransUrl;
    private String singleQueryUrl;
    private String batchTransUrl;
    private String fileTransUrl;
    private String appTransUrl;
    private String cardTransUrl;
    private String orderTransUrl;
    /**
     * 缴费产品
     */
    private String jfFrontTransUrl;
    private String jfBackTransUrl;
    private String jfSingleQueryUrl;
    private String jfCardTransUrl;
    private String jfAppTransUrl;

    public UnionPayConfig(UnionPaySignCertConfig signcert) {
        this.signcert = signcert;
    }
}
