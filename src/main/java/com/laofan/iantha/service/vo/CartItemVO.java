package com.laofan.iantha.service.vo;

import com.laofan.iantha.service.dto.CartItemDTO;
import java.util.List;

public class CartItemVO {

    private Long addressId;

    private List<CartItemDTO> cartItemDTOList;

    public List<CartItemDTO> getCartItemDTOList() {
        return cartItemDTOList;
    }

    public void setCartItemDTOList(List<CartItemDTO> cartItemDTOList) {
        this.cartItemDTOList = cartItemDTOList;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
