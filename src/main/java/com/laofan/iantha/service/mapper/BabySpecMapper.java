package com.laofan.iantha.service.mapper;

import com.laofan.iantha.domain.BabySpec;
import com.laofan.iantha.domain.Product;
import com.laofan.iantha.service.dto.BabySpecDTO;
import com.laofan.iantha.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BabySpec} and its DTO {@link BabySpecDTO}.
 */
@Mapper(componentModel = "spring")
public interface BabySpecMapper extends EntityMapper<BabySpecDTO, BabySpec> {
    @Mapping(target = "products", source = "products", qualifiedByName = "productId")
    BabySpecDTO toDto(BabySpec s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
