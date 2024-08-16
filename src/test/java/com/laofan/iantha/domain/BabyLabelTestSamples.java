package com.laofan.iantha.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BabyLabelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BabyLabel getBabyLabelSample1() {
        return new BabyLabel().id(1L).title("title1").labelCode("labelCode1").labelAttr("labelAttr1").identify("identify1");
    }

    public static BabyLabel getBabyLabelSample2() {
        return new BabyLabel().id(2L).title("title2").labelCode("labelCode2").labelAttr("labelAttr2").identify("identify2");
    }

    public static BabyLabel getBabyLabelRandomSampleGenerator() {
        return new BabyLabel()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .labelCode(UUID.randomUUID().toString())
            .labelAttr(UUID.randomUUID().toString())
            .identify(UUID.randomUUID().toString());
    }
}
