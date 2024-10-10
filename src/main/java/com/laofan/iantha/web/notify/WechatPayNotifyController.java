package com.laofan.iantha.web.notify;

import cn.hutool.core.convert.ConverterRegistry;
import com.github.binarywang.wxpay.bean.notify.OriginNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.laofan.iantha.config.RabbitMqConfig;
import com.laofan.iantha.service.mq.RabbitSendService;
import com.laofan.iantha.web.notify.constant.WechatPayConstant;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName WechatPayNotifyController
 * @Description 微信支付回调
 * @Date 2021/1/27 17:11
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/callback/wechatPay")
public class WechatPayNotifyController {

    private static final ConverterRegistry CONVERTER_REGISTRY = ConverterRegistry.getInstance();
    private static final Logger log = LoggerFactory.getLogger(WechatPayNotifyController.class);

    @Resource
    private RabbitSendService rabbitSendService;

    /**
     * 微信支付回调
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/notifyWxPay/{configId}")
    public String notifyWxPay(@PathVariable("configId") String configId, HttpServletRequest request) {
        try {
            log.info("微信支付回调获取到configId:{}", configId);
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            // 获取支付配置
            WxPayConfig wxPayConfig = getWxPayConfig(configId);
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);
            WxPayOrderNotifyV3Result notifyResult = wxPayService.parseOrderNotifyV3Result(xmlResult, null);
            OriginNotifyResponse notifyResponse = notifyResult.getRawData();
            if (notifyResponse.getEventType().equals(WechatPayConstant.EVENT_TYPE_SUCCESS)) {
                WxPayOrderNotifyV3Result.DecryptNotifyResult decryptNotifyResult = notifyResult.getResult();
                // 结果正确 outTradeNo
                String orderSn = decryptNotifyResult.getOutTradeNo();
                String tradeNo = decryptNotifyResult.getTransactionId();
                String attach = decryptNotifyResult.getAttach();
                String totalFee = BaseWxPayResult.fenToYuan(decryptNotifyResult.getAmount().getTotal());
                // 支付成功->执行操作
                log.info("商户生成的订单流水号：{{}}", orderSn);
                log.info("微信支付流水号：{{}}", tradeNo);
                log.info("支付金额/元：{{}}", totalFee);
                log.info("附加信息：{{}}", attach);
                log.info("支付成功->执行操作");
                // 组装发送消息参数
                /*NotifyResult notify = NotifyResult.builder()
                    .orderId(orderSn)
                    .tradeNo(tradeNo)
                    .totalAmount(CONVERTER_REGISTRY.convert(BigDecimal.class, totalFee))
                    .buyerLogonId("0.0.0.0")
                    .moduleName(attach)
                    .build();*/
                // 发送MQ消息
                //rabbitSendService.sendMessage(RabbitMqConfig.TOPIC_EXCHANGE, RabbitMqConfig.ROUTING_KEY, notify);
                //自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
                return WxPayNotifyResponse.success("服务器执行微信支付回调结果成功！");
            }
            return WxPayNotifyResponse.fail("code:" + -1 + "微信回调结果异常,异常原因:event_type值为" + notifyResponse.getEventType());
        } catch (Exception e) {
            log.error("微信回调结果异常,异常原因{{}}", e.getMessage());
            return WxPayNotifyResponse.fail("code:" + 9999 + "微信回调结果异常,异常原因:" + e.getMessage());
        }
    }

    /**
     * @Description 获取支付配置
     * @author Rick wlwq
     * @Date 2021/7/3 17:46
     */
    private WxPayConfig getWxPayConfig(String configId) {
        PayConfig config = null;
        if (config == null) {
            throw new RuntimeException("未获取到配置信息！请检查配置后重试！");
        }
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(config.getAppId());
        payConfig.setMchId(config.getMchId());
        payConfig.setMchKey(config.getMchKey());
        payConfig.setKeyPath(config.getKeyPath());
        payConfig.setApiV3Key(config.getApiV3Key());
        payConfig.setPrivateKeyPath(config.getApiclientKey());
        payConfig.setPrivateCertPath(config.getApiclientCert());
        payConfig.setCertSerialNo(config.getCertSerialNo());
        payConfig.setNotifyUrl(config.getNotifyUrl());
        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);
        return payConfig;
    }
}
