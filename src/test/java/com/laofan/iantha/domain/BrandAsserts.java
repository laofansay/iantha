package com.laofan.iantha.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BrandAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBrandAllPropertiesEquals(Brand expected, Brand actual) {
        assertBrandAutoGeneratedPropertiesEquals(expected, actual);
        assertBrandAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBrandAllUpdatablePropertiesEquals(Brand expected, Brand actual) {
        assertBrandUpdatableFieldsEquals(expected, actual);
        assertBrandUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBrandAutoGeneratedPropertiesEquals(Brand expected, Brand actual) {
        assertThat(expected)
            .as("Verify Brand auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBrandUpdatableFieldsEquals(Brand expected, Brand actual) {
        assertThat(expected)
            .as("Verify Brand relevant properties")
            .satisfies(e -> assertThat(e.getTitle()).as("check title").isEqualTo(actual.getTitle()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(e -> assertThat(e.getLogo()).as("check logo").isEqualTo(actual.getLogo()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBrandUpdatableRelationshipsEquals(Brand expected, Brand actual) {
        assertThat(expected)
            .as("Verify Brand relationships")
            .satisfies(e -> assertThat(e.getProducts()).as("check products").isEqualTo(actual.getProducts()));
    }
}
