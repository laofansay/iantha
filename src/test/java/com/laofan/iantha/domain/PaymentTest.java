package com.laofan.iantha.domain;

import static com.laofan.iantha.domain.OrderTestSamples.*;
import static com.laofan.iantha.domain.PaymentProviderTestSamples.*;
import static com.laofan.iantha.domain.PaymentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofan.iantha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payment.class);
        Payment payment1 = getPaymentSample1();
        Payment payment2 = new Payment();
        assertThat(payment1).isNotEqualTo(payment2);

        payment2.setId(payment1.getId());
        assertThat(payment1).isEqualTo(payment2);

        payment2 = getPaymentSample2();
        assertThat(payment1).isNotEqualTo(payment2);
    }

    @Test
    void orderTest() {
        Payment payment = getPaymentRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        payment.setOrder(orderBack);
        assertThat(payment.getOrder()).isEqualTo(orderBack);

        payment.order(null);
        assertThat(payment.getOrder()).isNull();
    }

    @Test
    void providerTest() {
        Payment payment = getPaymentRandomSampleGenerator();
        PaymentProvider paymentProviderBack = getPaymentProviderRandomSampleGenerator();

        payment.setProvider(paymentProviderBack);
        assertThat(payment.getProvider()).isEqualTo(paymentProviderBack);

        payment.provider(null);
        assertThat(payment.getProvider()).isNull();
    }
}
