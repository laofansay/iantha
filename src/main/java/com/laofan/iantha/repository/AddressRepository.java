package com.laofan.iantha.repository;

import com.laofan.iantha.domain.Address;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


    @Modifying
    @Query("UPDATE Address e SET e.master = false,e.tag=:ident " +
        " WHERE  e.id <> :id")
    void updateUnMasterWithOther(@Param("id")Long id, @Param("ident") String ident);
}
