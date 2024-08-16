package com.laofan.iantha.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CartItemAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCartItemAllPropertiesEquals(CartItem expected, CartItem actual) {
        assertCartItemAutoGeneratedPropertiesEquals(expected, actual);
        assertCartItemAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCartItemAllUpdatablePropertiesEquals(CartItem expected, CartItem actual) {
        assertCartItemUpdatableFieldsEquals(expected, actual);
        assertCartItemUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCartItemAutoGeneratedPropertiesEquals(CartItem expected, CartItem actual) {
        assertThat(expected)
            .as("Verify CartItem auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCartItemUpdatableFieldsEquals(CartItem expected, CartItem actual) {
        assertThat(expected)
            .as("Verify CartItem relevant properties")
            .satisfies(e -> assertThat(e.getCartId()).as("check cartId").isEqualTo(actual.getCartId()))
            .satisfies(e -> assertThat(e.getProductId()).as("check productId").isEqualTo(actual.getProductId()))
            .satisfies(e -> assertThat(e.getCount()).as("check count").isEqualTo(actual.getCount()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCartItemUpdatableRelationshipsEquals(CartItem expected, CartItem actual) {
        assertThat(expected)
            .as("Verify CartItem relationships")
            .satisfies(e -> assertThat(e.getProduct()).as("check product").isEqualTo(actual.getProduct()));
    }
}
