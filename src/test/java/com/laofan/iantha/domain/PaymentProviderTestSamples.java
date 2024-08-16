package com.laofan.iantha.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PaymentProviderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PaymentProvider getPaymentProviderSample1() {
        return new PaymentProvider().id(1L).title("title1").description("description1").websiteUrl("websiteUrl1");
    }

    public static PaymentProvider getPaymentProviderSample2() {
        return new PaymentProvider().id(2L).title("title2").description("description2").websiteUrl("websiteUrl2");
    }

    public static PaymentProvider getPaymentProviderRandomSampleGenerator() {
        return new PaymentProvider()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .websiteUrl(UUID.randomUUID().toString());
    }
}
