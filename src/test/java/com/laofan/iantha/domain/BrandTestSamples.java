package com.laofan.iantha.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BrandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Brand getBrandSample1() {
        return new Brand().id(1L).title("title1").description("description1").logo("logo1");
    }

    public static Brand getBrandSample2() {
        return new Brand().id(2L).title("title2").description("description2").logo("logo2");
    }

    public static Brand getBrandRandomSampleGenerator() {
        return new Brand()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .logo(UUID.randomUUID().toString());
    }
}
