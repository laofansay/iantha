package com.laofan.iantha.domain;

import static com.laofan.iantha.domain.BrandTestSamples.*;
import static com.laofan.iantha.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofan.iantha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BrandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Brand.class);
        Brand brand1 = getBrandSample1();
        Brand brand2 = new Brand();
        assertThat(brand1).isNotEqualTo(brand2);

        brand2.setId(brand1.getId());
        assertThat(brand1).isEqualTo(brand2);

        brand2 = getBrandSample2();
        assertThat(brand1).isNotEqualTo(brand2);
    }

    @Test
    void productsTest() {
        Brand brand = getBrandRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        brand.setProducts(productBack);
        assertThat(brand.getProducts()).isEqualTo(productBack);

        brand.products(null);
        assertThat(brand.getProducts()).isNull();
    }
}
