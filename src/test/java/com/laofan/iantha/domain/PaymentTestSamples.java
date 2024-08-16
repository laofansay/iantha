package com.laofan.iantha.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PaymentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Payment getPaymentSample1() {
        return new Payment().id(1L).number(1).status("status1").refId("refId1").cardPan("cardPan1").cardHash("cardHash1");
    }

    public static Payment getPaymentSample2() {
        return new Payment().id(2L).number(2).status("status2").refId("refId2").cardPan("cardPan2").cardHash("cardHash2");
    }

    public static Payment getPaymentRandomSampleGenerator() {
        return new Payment()
            .id(longCount.incrementAndGet())
            .number(intCount.incrementAndGet())
            .status(UUID.randomUUID().toString())
            .refId(UUID.randomUUID().toString())
            .cardPan(UUID.randomUUID().toString())
            .cardHash(UUID.randomUUID().toString());
    }
}
