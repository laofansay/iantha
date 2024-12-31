package com.laofan.iantha.service.dto;

import com.laofan.iantha.domain.Address;
import com.laofan.iantha.domain.CartItem;
import com.laofan.iantha.domain.enumeration.EPaymentMethod;
import com.laofan.iantha.stripe.dto.CreditCardInfo;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.laofan.iantha.domain.Order} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderFromDTO implements Serializable {

    /**
     * 收货地址
     */
    @NotNull
    private Address address;

    //购物车商品信息
    @NotNull
    private List<CartItem> carts;

    @NotNull
    private EPaymentMethod total;

    //信用卡信息 由 stripe提供的creditCardInfo DTO来接收
    private CreditCardInfo creditCardInfo;

    public @NotNull Address getAddress() {
        return address;
    }

    public void setAddress(@NotNull Address address) {
        this.address = address;
    }

    public @NotNull List<CartItem> getCarts() {
        return carts;
    }

    public void setCarts(@NotNull List<CartItem> carts) {
        this.carts = carts;
    }

    public @NotNull EPaymentMethod getTotal() {
        return total;
    }

    public void setTotal(@NotNull EPaymentMethod total) {
        this.total = total;
    }

    public CreditCardInfo getCreditCardInfo() {
        return creditCardInfo;
    }

    public void setCreditCardInfo(CreditCardInfo creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }
}
