package com.laofan.iantha.domain;

import static com.laofan.iantha.domain.DiscountCodeTestSamples.*;
import static com.laofan.iantha.domain.OrderTestSamples.*;
import static com.laofan.iantha.domain.PaymentTestSamples.*;
import static com.laofan.iantha.domain.RefundTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofan.iantha.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order.class);
        Order order1 = getOrderSample1();
        Order order2 = new Order();
        assertThat(order1).isNotEqualTo(order2);

        order2.setId(order1.getId());
        assertThat(order1).isEqualTo(order2);

        order2 = getOrderSample2();
        assertThat(order1).isNotEqualTo(order2);
    }

    @Test
    void refundTest() {
        Order order = getOrderRandomSampleGenerator();
        Refund refundBack = getRefundRandomSampleGenerator();

        order.setRefund(refundBack);
        assertThat(order.getRefund()).isEqualTo(refundBack);

        order.refund(null);
        assertThat(order.getRefund()).isNull();
    }

    @Test
    void paymentsTest() {
        Order order = getOrderRandomSampleGenerator();
        Payment paymentBack = getPaymentRandomSampleGenerator();

        order.addPayments(paymentBack);
        assertThat(order.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getOrder()).isEqualTo(order);

        order.removePayments(paymentBack);
        assertThat(order.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getOrder()).isNull();

        order.payments(new HashSet<>(Set.of(paymentBack)));
        assertThat(order.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getOrder()).isEqualTo(order);

        order.setPayments(new HashSet<>());
        assertThat(order.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getOrder()).isNull();
    }

    @Test
    void discountCodeTest() {
        Order order = getOrderRandomSampleGenerator();
        DiscountCode discountCodeBack = getDiscountCodeRandomSampleGenerator();

        order.addDiscountCode(discountCodeBack);
        assertThat(order.getDiscountCodes()).containsOnly(discountCodeBack);
        assertThat(discountCodeBack.getOrder()).isEqualTo(order);

        order.removeDiscountCode(discountCodeBack);
        assertThat(order.getDiscountCodes()).doesNotContain(discountCodeBack);
        assertThat(discountCodeBack.getOrder()).isNull();

        order.discountCodes(new HashSet<>(Set.of(discountCodeBack)));
        assertThat(order.getDiscountCodes()).containsOnly(discountCodeBack);
        assertThat(discountCodeBack.getOrder()).isEqualTo(order);

        order.setDiscountCodes(new HashSet<>());
        assertThat(order.getDiscountCodes()).doesNotContain(discountCodeBack);
        assertThat(discountCodeBack.getOrder()).isNull();
    }
}
