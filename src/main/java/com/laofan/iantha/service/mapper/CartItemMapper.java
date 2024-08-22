package com.laofan.iantha.service.mapper;

import com.laofan.iantha.domain.CartItem;
import com.laofan.iantha.domain.Product;
import com.laofan.iantha.service.dto.CartItemDTO;
import com.laofan.iantha.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CartItem} and its DTO {@link CartItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface CartItemMapper extends EntityMapper<CartItemDTO, CartItem> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    CartItemDTO toDto(CartItem s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "transCode", source = "transCode")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "images", source = "images")
    @Mapping(target = "keywords", source = "keywords")
    @Mapping(target = "metadata", source = "metadata")
    @Mapping(target = "guidePrice", source = "guidePrice")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "showPrice", source = "showPrice")
    @Mapping(target = "discount", source = "discount")
    @Mapping(target = "stock", source = "stock")
    ProductDTO toDtoProductId(Product product);
}
