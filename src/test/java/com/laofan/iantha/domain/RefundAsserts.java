package com.laofan.iantha.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RefundAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRefundAllPropertiesEquals(Refund expected, Refund actual) {
        assertRefundAutoGeneratedPropertiesEquals(expected, actual);
        assertRefundAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRefundAllUpdatablePropertiesEquals(Refund expected, Refund actual) {
        assertRefundUpdatableFieldsEquals(expected, actual);
        assertRefundUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRefundAutoGeneratedPropertiesEquals(Refund expected, Refund actual) {
        assertThat(expected)
            .as("Verify Refund auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRefundUpdatableFieldsEquals(Refund expected, Refund actual) {
        assertThat(expected)
            .as("Verify Refund relevant properties")
            .satisfies(e -> assertThat(e.getAmount()).as("check amount").isEqualTo(actual.getAmount()))
            .satisfies(e -> assertThat(e.getReason()).as("check reason").isEqualTo(actual.getReason()))
            .satisfies(e -> assertThat(e.getCreatedAt()).as("check createdAt").isEqualTo(actual.getCreatedAt()))
            .satisfies(e -> assertThat(e.getUpdatedAt()).as("check updatedAt").isEqualTo(actual.getUpdatedAt()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRefundUpdatableRelationshipsEquals(Refund expected, Refund actual) {}
}
