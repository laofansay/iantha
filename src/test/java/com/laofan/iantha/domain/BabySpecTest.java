package com.laofan.iantha.domain;

import static com.laofan.iantha.domain.BabySpecTestSamples.*;
import static com.laofan.iantha.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofan.iantha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BabySpecTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BabySpec.class);
        BabySpec babySpec1 = getBabySpecSample1();
        BabySpec babySpec2 = new BabySpec();
        assertThat(babySpec1).isNotEqualTo(babySpec2);

        babySpec2.setId(babySpec1.getId());
        assertThat(babySpec1).isEqualTo(babySpec2);

        babySpec2 = getBabySpecSample2();
        assertThat(babySpec1).isNotEqualTo(babySpec2);
    }

    @Test
    void productsTest() {
        BabySpec babySpec = getBabySpecRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        babySpec.setProducts(productBack);
        assertThat(babySpec.getProducts()).isEqualTo(productBack);

        babySpec.products(null);
        assertThat(babySpec.getProducts()).isNull();
    }
}
