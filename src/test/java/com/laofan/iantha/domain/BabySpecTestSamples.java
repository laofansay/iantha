package com.laofan.iantha.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BabySpecTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static BabySpec getBabySpecSample1() {
        return new BabySpec()
            .id(1L)
            .specCode("specCode1")
            .specTitle("specTitle1")
            .description("description1")
            .specQuantity(1)
            .images("images1")
            .sellCount(1)
            .stockCount(1);
    }

    public static BabySpec getBabySpecSample2() {
        return new BabySpec()
            .id(2L)
            .specCode("specCode2")
            .specTitle("specTitle2")
            .description("description2")
            .specQuantity(2)
            .images("images2")
            .sellCount(2)
            .stockCount(2);
    }

    public static BabySpec getBabySpecRandomSampleGenerator() {
        return new BabySpec()
            .id(longCount.incrementAndGet())
            .specCode(UUID.randomUUID().toString())
            .specTitle(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .specQuantity(intCount.incrementAndGet())
            .images(UUID.randomUUID().toString())
            .sellCount(intCount.incrementAndGet())
            .stockCount(intCount.incrementAndGet());
    }
}
