package com.laofan.iantha.repository;

import com.laofan.iantha.domain.CartItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CartItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Modifying
    @Query("UPDATE CartItem e SET e.count = :count " +
          " WHERE e.cid = :cid and e.prodId= :prodId")
    void updateCount(@Param("cid") String cid,
                                 @Param("prodId") String prodId,
                                 @Param("count") Integer count);


}
