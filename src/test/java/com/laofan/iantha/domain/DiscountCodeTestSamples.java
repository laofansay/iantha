package com.laofan.iantha.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DiscountCodeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static DiscountCode getDiscountCodeSample1() {
        return new DiscountCode().id(1L).code("code1").stock(1).description("description1").percent(1);
    }

    public static DiscountCode getDiscountCodeSample2() {
        return new DiscountCode().id(2L).code("code2").stock(2).description("description2").percent(2);
    }

    public static DiscountCode getDiscountCodeRandomSampleGenerator() {
        return new DiscountCode()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .stock(intCount.incrementAndGet())
            .description(UUID.randomUUID().toString())
            .percent(intCount.incrementAndGet());
    }
}
