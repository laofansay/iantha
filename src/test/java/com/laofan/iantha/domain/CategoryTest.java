package com.laofan.iantha.domain;

import static com.laofan.iantha.domain.CategoryTestSamples.*;
import static com.laofan.iantha.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofan.iantha.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = getCategorySample1();
        Category category2 = new Category();
        assertThat(category1).isNotEqualTo(category2);

        category2.setId(category1.getId());
        assertThat(category1).isEqualTo(category2);

        category2 = getCategorySample2();
        assertThat(category1).isNotEqualTo(category2);
    }

    @Test
    void productsTest() {
        Category category = getCategoryRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        category.addProducts(productBack);
        assertThat(category.getProducts()).containsOnly(productBack);
        assertThat(productBack.getCategories()).isEqualTo(category);

        category.removeProducts(productBack);
        assertThat(category.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getCategories()).isNull();

        category.products(new HashSet<>(Set.of(productBack)));
        assertThat(category.getProducts()).containsOnly(productBack);
        assertThat(productBack.getCategories()).isEqualTo(category);

        category.setProducts(new HashSet<>());
        assertThat(category.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getCategories()).isNull();
    }
}
