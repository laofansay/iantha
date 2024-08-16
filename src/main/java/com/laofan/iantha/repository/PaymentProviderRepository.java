package com.laofan.iantha.repository;

import com.laofan.iantha.domain.PaymentProvider;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaymentProvider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentProviderRepository extends JpaRepository<PaymentProvider, Long> {}
