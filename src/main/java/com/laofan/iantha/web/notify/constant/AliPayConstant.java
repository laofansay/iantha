package com.laofan.iantha.web.notify.constant;

/**
 * @ClassName AliPayConstant
 * @Description 支付宝支付使用的常量
 * @Date 2020/12/3 15:31
 * @Author Rick wlwq
 */
public class AliPayConstant {

    /**
     * 表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等
     */
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";

    /**
     * 表示交易已经成功结束，并不能再对该交易做后续操作
     */
    public static final String TRADE_FINISHED = "TRADE_FINISHED";

    /**
     * 成功
     */
    public static final String SUCCESS = "Y";

    /**
     * 支付宝查询支付订单->状态为正常
     */
    public static final String QUERY_ORDER_STATUS_IS_SUCCESS = "TRADE_SUCCESS";

    /**
     * 阿里云支付API地址
     */
    public static final String SERVER_API_URL = "https://openapi.alipay.com/gateway.do";

    /**
     * 请求格式
     */
    public static final String FORMAT = "json";

    /**
     * 字符集
     */
    public static final String CHARSET = "UTF-8";

    /**
     * 签名类型
     */
    public static final String SIGN_TYPE = "RSA2";
}
