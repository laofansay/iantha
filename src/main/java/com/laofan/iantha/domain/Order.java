package com.laofan.iantha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false, unique = true)
    private Integer number;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @NotNull
    @Column(name = "shipping", nullable = false)
    private Float shipping;

    @NotNull
    @Column(name = "payable", nullable = false)
    private BigDecimal payable;

    @NotNull
    @Column(name = "tax", nullable = false)
    private Float tax;

    @NotNull
    @Column(name = "discount", nullable = false)
    private BigDecimal discount;

    @NotNull
    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @NotNull
    @Column(name = "completed", nullable = false)
    private Boolean completed;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "order", "provider" }, allowSetters = true)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "order" }, allowSetters = true)
    private Set<DiscountCode> discountCodes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product", "orderItem" }, allowSetters = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return this.number;
    }

    public Order number(Integer number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStatus() {
        return this.status;
    }

    public Order status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public Order total(BigDecimal total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Float getShipping() {
        return this.shipping;
    }

    public Order shipping(Float shipping) {
        this.setShipping(shipping);
        return this;
    }

    public void setShipping(Float shipping) {
        this.shipping = shipping;
    }

    public BigDecimal getPayable() {
        return this.payable;
    }

    public Order payable(BigDecimal payable) {
        this.setPayable(payable);
        return this;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public Float getTax() {
        return this.tax;
    }

    public Order tax(Float tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public BigDecimal getDiscount() {
        return this.discount;
    }

    public Order discount(BigDecimal discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Boolean getPaid() {
        return this.paid;
    }

    public Order paid(Boolean paid) {
        this.setPaid(paid);
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public Order completed(Boolean completed) {
        this.setCompleted(completed);
        return this;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Order createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Order updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setOrder(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setOrder(this));
        }
        this.payments = payments;
    }

    public Order payments(Set<Payment> payments) {
        this.setPayments(payments);
        return this;
    }

    public Order addPayments(Payment payment) {
        this.payments.add(payment);
        payment.setOrder(this);
        return this;
    }

    public Order removePayments(Payment payment) {
        this.payments.remove(payment);
        payment.setOrder(null);
        return this;
    }

    public Set<DiscountCode> getDiscountCodes() {
        return this.discountCodes;
    }

    public void setDiscountCodes(Set<DiscountCode> discountCodes) {
        if (this.discountCodes != null) {
            this.discountCodes.forEach(i -> i.setOrder(null));
        }
        if (discountCodes != null) {
            discountCodes.forEach(i -> i.setOrder(this));
        }
        this.discountCodes = discountCodes;
    }

    public Order discountCodes(Set<DiscountCode> discountCodes) {
        this.setDiscountCodes(discountCodes);
        return this;
    }

    public Order addDiscountCode(DiscountCode discountCode) {
        this.discountCodes.add(discountCode);
        discountCode.setOrder(this);
        return this;
    }

    public Order removeDiscountCode(DiscountCode discountCode) {
        this.discountCodes.remove(discountCode);
        discountCode.setOrder(null);
        return this;
    }

    public Set<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        if (this.orderItems != null) {
            this.orderItems.forEach(i -> i.setOrderItem(null));
        }
        if (orderItems != null) {
            orderItems.forEach(i -> i.setOrderItem(this));
        }
        this.orderItems = orderItems;
    }

    public Order orderItems(Set<OrderItem> orderItems) {
        this.setOrderItems(orderItems);
        return this;
    }

    public Order addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrderItem(this);
        return this;
    }

    public Order removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrderItem(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return getId() != null && getId().equals(((Order) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", status='" + getStatus() + "'" +
            ", total=" + getTotal() +
            ", shipping=" + getShipping() +
            ", payable=" + getPayable() +
            ", tax=" + getTax() +
            ", discount=" + getDiscount() +
            ", paid='" + getPaid() + "'" +
            ", completed='" + getCompleted() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
