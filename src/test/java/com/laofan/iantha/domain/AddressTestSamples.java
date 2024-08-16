package com.laofan.iantha.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AddressTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Address getAddressSample1() {
        return new Address().id(1L).country("country1").address("address1").city("city1").phone("phone1").postalCode("postalCode1");
    }

    public static Address getAddressSample2() {
        return new Address().id(2L).country("country2").address("address2").city("city2").phone("phone2").postalCode("postalCode2");
    }

    public static Address getAddressRandomSampleGenerator() {
        return new Address()
            .id(longCount.incrementAndGet())
            .country(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .postalCode(UUID.randomUUID().toString());
    }
}
