package com.laofan.iantha.service.mapper;

import com.laofan.iantha.domain.Brand;
import com.laofan.iantha.domain.Category;
import com.laofan.iantha.domain.Product;
import com.laofan.iantha.service.dto.BrandDTO;
import com.laofan.iantha.service.dto.CategoryDTO;
import com.laofan.iantha.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "brand", source = "brand", qualifiedByName = "brandId")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "categoryId")
    ProductDTO toDto(Product s);

    @Named("brandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BrandDTO toDtoBrandId(Brand brand);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);
}
