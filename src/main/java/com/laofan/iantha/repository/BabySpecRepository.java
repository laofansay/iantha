package com.laofan.iantha.repository;

import com.laofan.iantha.domain.BabySpec;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BabySpec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BabySpecRepository extends JpaRepository<BabySpec, Long> {}
