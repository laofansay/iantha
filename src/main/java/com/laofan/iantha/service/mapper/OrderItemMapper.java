package com.laofan.iantha.service.mapper;

import com.laofan.iantha.domain.Order;
import com.laofan.iantha.domain.OrderItem;
import com.laofan.iantha.domain.Product;
import com.laofan.iantha.service.dto.OrderDTO;
import com.laofan.iantha.service.dto.OrderItemDTO;
import com.laofan.iantha.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderItem} and its DTO {@link OrderItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    @Mapping(target = "orderItem", source = "orderItem", qualifiedByName = "orderId")
    OrderItemDTO toDto(OrderItem s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("orderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoOrderId(Order order);
}
