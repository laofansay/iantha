package com.laofan.iantha.domain;

import static com.laofan.iantha.domain.PaymentProviderTestSamples.*;
import static com.laofan.iantha.domain.PaymentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofan.iantha.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PaymentProviderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentProvider.class);
        PaymentProvider paymentProvider1 = getPaymentProviderSample1();
        PaymentProvider paymentProvider2 = new PaymentProvider();
        assertThat(paymentProvider1).isNotEqualTo(paymentProvider2);

        paymentProvider2.setId(paymentProvider1.getId());
        assertThat(paymentProvider1).isEqualTo(paymentProvider2);

        paymentProvider2 = getPaymentProviderSample2();
        assertThat(paymentProvider1).isNotEqualTo(paymentProvider2);
    }

    @Test
    void ordersTest() {
        PaymentProvider paymentProvider = getPaymentProviderRandomSampleGenerator();
        Payment paymentBack = getPaymentRandomSampleGenerator();

        paymentProvider.addOrders(paymentBack);
        assertThat(paymentProvider.getOrders()).containsOnly(paymentBack);
        assertThat(paymentBack.getProvider()).isEqualTo(paymentProvider);

        paymentProvider.removeOrders(paymentBack);
        assertThat(paymentProvider.getOrders()).doesNotContain(paymentBack);
        assertThat(paymentBack.getProvider()).isNull();

        paymentProvider.orders(new HashSet<>(Set.of(paymentBack)));
        assertThat(paymentProvider.getOrders()).containsOnly(paymentBack);
        assertThat(paymentBack.getProvider()).isEqualTo(paymentProvider);

        paymentProvider.setOrders(new HashSet<>());
        assertThat(paymentProvider.getOrders()).doesNotContain(paymentBack);
        assertThat(paymentBack.getProvider()).isNull();
    }
}
