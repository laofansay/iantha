package com.laofan.iantha.repository;

import com.laofan.iantha.domain.OrderItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
