package com.laofan.iantha.web.notify;

/**
 * 支付配置2.0版对象 pay_config
 *
 * @author Rick wlwq
 * @date 2021-07-02
 */
public class PayConfig {

    private static final long serialVersionUID = 1L;

    /** 支付配置ID */
    private String payConfigId;

    /** 应用名字 */
    private String applicationName;

    /** 支付平台（alipay支付宝，wx微信） */
    private String payPlatform;

    /** 支付方式（alipay_app=支付宝APP支付，alipay_pc=支付宝pc支付，alipay_wap=支付宝wap支付，JSAPI=微信公众号小程序支付，MWEB=微信H5支付，NATIVE=微信Native支付，APP=微信APP支付） */
    private String payType;

    /** APP_ID */
    private String appId;

    /** APP_SECRET */
    private String appSecret;

    /** 支付宝应用私钥 */
    private String privateKey;

    /** 支付回调地址 */
    private String notifyUrl;

    /** 支付宝应用公钥证书路径（appCertPublicKey_2021.crt） */
    private String certPath;

    /** 支付宝公钥证书路径（alipayCertPublicKey_RSA2.crt） */
    private String publicCertPath;

    /** 支付宝根证书路径（alipayRootCert.crt） */
    private String rootCertPath;

    /** 微信支付商户号 */
    private String mchId;

    /** 微信支付商户秘钥 */
    private String mchKey;

    /** 服务商模式下的子商户公众账号ID */
    private String subAppId;

    /** 服务商模式下的子商户号 */
    private String subMchId;

    /** 微信支付证书的位置 */
    private String keyPath;

    /** 微信支付apiclient_key.pem证书文件（暂时不用） */
    private String apiclientKey;

    /** 微信支付apiclient_cert.pem证书文件（暂时不用） */
    private String apiclientCert;

    /** apiV3秘钥值（暂时不用） */
    private String apiV3Key;

    /** apiV3证书序列号值（暂时不用） */
    private String certSerialNo;

    /** 是否启用（1启用 0关闭） */
    private Integer startStatus;

    public String getPayConfigId() {
        return payConfigId;
    }

    public void setPayConfigId(String payConfigId) {
        this.payConfigId = payConfigId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getPublicCertPath() {
        return publicCertPath;
    }

    public void setPublicCertPath(String publicCertPath) {
        this.publicCertPath = publicCertPath;
    }

    public String getRootCertPath() {
        return rootCertPath;
    }

    public void setRootCertPath(String rootCertPath) {
        this.rootCertPath = rootCertPath;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getApiclientKey() {
        return apiclientKey;
    }

    public void setApiclientKey(String apiclientKey) {
        this.apiclientKey = apiclientKey;
    }

    public String getApiclientCert() {
        return apiclientCert;
    }

    public void setApiclientCert(String apiclientCert) {
        this.apiclientCert = apiclientCert;
    }

    public String getApiV3Key() {
        return apiV3Key;
    }

    public void setApiV3Key(String apiV3Key) {
        this.apiV3Key = apiV3Key;
    }

    public String getCertSerialNo() {
        return certSerialNo;
    }

    public void setCertSerialNo(String certSerialNo) {
        this.certSerialNo = certSerialNo;
    }

    public Integer getStartStatus() {
        return startStatus;
    }

    public void setStartStatus(Integer startStatus) {
        this.startStatus = startStatus;
    }
}
