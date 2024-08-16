package com.laofan.iantha.repository;

import com.laofan.iantha.domain.BabyLabel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BabyLabel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BabyLabelRepository extends JpaRepository<BabyLabel, Long> {}
