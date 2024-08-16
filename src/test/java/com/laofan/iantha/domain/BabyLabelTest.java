package com.laofan.iantha.domain;

import static com.laofan.iantha.domain.BabyLabelTestSamples.*;
import static com.laofan.iantha.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofan.iantha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BabyLabelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BabyLabel.class);
        BabyLabel babyLabel1 = getBabyLabelSample1();
        BabyLabel babyLabel2 = new BabyLabel();
        assertThat(babyLabel1).isNotEqualTo(babyLabel2);

        babyLabel2.setId(babyLabel1.getId());
        assertThat(babyLabel1).isEqualTo(babyLabel2);

        babyLabel2 = getBabyLabelSample2();
        assertThat(babyLabel1).isNotEqualTo(babyLabel2);
    }

    @Test
    void productsTest() {
        BabyLabel babyLabel = getBabyLabelRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        babyLabel.setProducts(productBack);
        assertThat(babyLabel.getProducts()).isEqualTo(productBack);

        babyLabel.products(null);
        assertThat(babyLabel.getProducts()).isNull();
    }
}
