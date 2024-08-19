package com.laofan.iantha.domain;

import static com.laofan.iantha.domain.BabyLabelTestSamples.*;
import static com.laofan.iantha.domain.BabySpecTestSamples.*;
import static com.laofan.iantha.domain.BrandTestSamples.*;
import static com.laofan.iantha.domain.CartItemTestSamples.*;
import static com.laofan.iantha.domain.CategoryTestSamples.*;
import static com.laofan.iantha.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofan.iantha.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void brandTest() {
        Product product = getProductRandomSampleGenerator();
        Brand brandBack = getBrandRandomSampleGenerator();

        product.setBrand(brandBack);
        assertThat(product.getBrand()).isEqualTo(brandBack);

        product.brand(null);
        assertThat(product.getBrand()).isNull();
    }

    @Test
    void categoriesTest() {
        Product product = getProductRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        product.setCategories(categoryBack);
        assertThat(product.getCategories()).isEqualTo(categoryBack);

        product.categories(null);
        assertThat(product.getCategories()).isNull();
    }

    @Test
    void labelTest() {
        Product product = getProductRandomSampleGenerator();
        BabyLabel babyLabelBack = getBabyLabelRandomSampleGenerator();

        product.addLabel(babyLabelBack);
        assertThat(product.getLabels()).containsOnly(babyLabelBack);
        assertThat(babyLabelBack.getProducts()).isEqualTo(product);

        product.removeLabel(babyLabelBack);
        assertThat(product.getLabels()).doesNotContain(babyLabelBack);
        assertThat(babyLabelBack.getProducts()).isNull();

        product.labels(new HashSet<>(Set.of(babyLabelBack)));
        assertThat(product.getLabels()).containsOnly(babyLabelBack);
        assertThat(babyLabelBack.getProducts()).isEqualTo(product);

        product.setLabels(new HashSet<>());
        assertThat(product.getLabels()).doesNotContain(babyLabelBack);
        assertThat(babyLabelBack.getProducts()).isNull();
    }

    @Test
    void cartItemsTest() {
        Product product = getProductRandomSampleGenerator();
        CartItem cartItemBack = getCartItemRandomSampleGenerator();

        product.addCartItems(cartItemBack);
        assertThat(product.getCartItems()).containsOnly(cartItemBack);
        assertThat(cartItemBack.getProduct()).isEqualTo(product);

        product.removeCartItems(cartItemBack);
        assertThat(product.getCartItems()).doesNotContain(cartItemBack);
        assertThat(cartItemBack.getProduct()).isNull();

        product.cartItems(new HashSet<>(Set.of(cartItemBack)));
        assertThat(product.getCartItems()).containsOnly(cartItemBack);
        assertThat(cartItemBack.getProduct()).isEqualTo(product);

        product.setCartItems(new HashSet<>());
        assertThat(product.getCartItems()).doesNotContain(cartItemBack);
        assertThat(cartItemBack.getProduct()).isNull();
    }

    @Test
    void specTest() {
        Product product = getProductRandomSampleGenerator();
        BabySpec babySpecBack = getBabySpecRandomSampleGenerator();

        product.addSpec(babySpecBack);
        assertThat(product.getSpecs()).containsOnly(babySpecBack);
        assertThat(babySpecBack.getProducts()).isEqualTo(product);

        product.removeSpec(babySpecBack);
        assertThat(product.getSpecs()).doesNotContain(babySpecBack);
        assertThat(babySpecBack.getProducts()).isNull();

        product.specs(new HashSet<>(Set.of(babySpecBack)));
        assertThat(product.getSpecs()).containsOnly(babySpecBack);
        assertThat(babySpecBack.getProducts()).isEqualTo(product);

        product.setSpecs(new HashSet<>());
        assertThat(product.getSpecs()).doesNotContain(babySpecBack);
        assertThat(babySpecBack.getProducts()).isNull();
    }
}
