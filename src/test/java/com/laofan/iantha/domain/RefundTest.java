package com.laofan.iantha.domain;

import static com.laofan.iantha.domain.OrderTestSamples.*;
import static com.laofan.iantha.domain.RefundTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofan.iantha.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RefundTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Refund.class);
        Refund refund1 = getRefundSample1();
        Refund refund2 = new Refund();
        assertThat(refund1).isNotEqualTo(refund2);

        refund2.setId(refund1.getId());
        assertThat(refund1).isEqualTo(refund2);

        refund2 = getRefundSample2();
        assertThat(refund1).isNotEqualTo(refund2);
    }

    @Test
    void orderTest() {
        Refund refund = getRefundRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        refund.addOrder(orderBack);
        assertThat(refund.getOrders()).containsOnly(orderBack);
        assertThat(orderBack.getRefund()).isEqualTo(refund);

        refund.removeOrder(orderBack);
        assertThat(refund.getOrders()).doesNotContain(orderBack);
        assertThat(orderBack.getRefund()).isNull();

        refund.orders(new HashSet<>(Set.of(orderBack)));
        assertThat(refund.getOrders()).containsOnly(orderBack);
        assertThat(orderBack.getRefund()).isEqualTo(refund);

        refund.setOrders(new HashSet<>());
        assertThat(refund.getOrders()).doesNotContain(orderBack);
        assertThat(orderBack.getRefund()).isNull();
    }
}
