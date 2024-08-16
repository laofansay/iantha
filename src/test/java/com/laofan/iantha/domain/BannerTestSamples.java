package com.laofan.iantha.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BannerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Banner getBannerSample1() {
        return new Banner().id(1L).label("label1").images("images1");
    }

    public static Banner getBannerSample2() {
        return new Banner().id(2L).label("label2").images("images2");
    }

    public static Banner getBannerRandomSampleGenerator() {
        return new Banner().id(longCount.incrementAndGet()).label(UUID.randomUUID().toString()).images(UUID.randomUUID().toString());
    }
}
