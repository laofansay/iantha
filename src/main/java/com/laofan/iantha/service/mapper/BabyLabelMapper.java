package com.laofan.iantha.service.mapper;

import com.laofan.iantha.domain.BabyLabel;
import com.laofan.iantha.domain.Product;
import com.laofan.iantha.service.dto.BabyLabelDTO;
import com.laofan.iantha.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BabyLabel} and its DTO {@link BabyLabelDTO}.
 */
@Mapper(componentModel = "spring")
public interface BabyLabelMapper extends EntityMapper<BabyLabelDTO, BabyLabel> {
    @Mapping(target = "products", source = "products", qualifiedByName = "productId")
    BabyLabelDTO toDto(BabyLabel s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
