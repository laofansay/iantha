package com.laofan.iantha.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product()
            .id(1L)
            .title("title1")
            .transCode("transCode1")
            .description("description1")
            .images(new String[]{"images1"})
            .keywords(new String[]{"keywords1"})
            .discount(1)
            .stock(1)
            .sellCount(1)
            .stockCount(1);
    }

    public static Product getProductSample2() {
        return new Product()
            .id(2L)
            .title("title2")
            .transCode("transCode2")
            .description("description2")
            .images(new String[]{"images1"})
            .keywords(new String[]{"keywords1"})
            .discount(2)
            .stock(2)
            .sellCount(2)
            .stockCount(2);
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .transCode(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .images(new String[]{"images1"})
            .keywords(new String[]{"keywords1"})
            .discount(intCount.incrementAndGet())
            .stock(intCount.incrementAndGet())
            .sellCount(intCount.incrementAndGet())
            .stockCount(intCount.incrementAndGet());
    }
}
