package com.laofan.iantha.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Category getCategorySample1() {
        return new Category().id(1L).title("title1").description("description1").cateCode("cateCode1").icon("icon1");
    }

    public static Category getCategorySample2() {
        return new Category().id(2L).title("title2").description("description2").cateCode("cateCode2").icon("icon2");
    }

    public static Category getCategoryRandomSampleGenerator() {
        return new Category()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .cateCode(UUID.randomUUID().toString())
            .icon(UUID.randomUUID().toString());
    }
}
