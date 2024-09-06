package com.laofan.iantha.web.notify.constant;

/**
 * @ClassName ModuleConstant
 * @Description 模块名字常量类型
 * @Date 2021/1/27 15:01
 * @Author Rick wlwq
 */
public class ModuleConstant {

    /**
     * 商品支付
     */
    public static final String GOOD_MODULE = "good";

    /**
     * 会员支付
     */
    public static final String MEMBER_MODULE = "member";

    /**
     * 服务支付
     */
    public static final String SERVE_MODULE = "serveOrder";

    /**
     * 自动下单
     */
    public static final String AUTO_PLACE_STORE_ORDER = "autoPlaceStoreOrder";

    /**
     * 商品订单支付超时
     */
    public static final String STORE_ORDER_PAY_TIMEOUT = "goodOrderPayTimeout";

    /**
     * 服务订单支付超时
     */
    public static final String SERVE_ORDER_PAY_TIMEOUT = "serveOrderPayTimeout";

    /**
     * 发送订单邮件
     */
    public static final String SEND_STORE_ORDER_EMAIL = "sendStoreOrderEmail";
}
