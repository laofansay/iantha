package com.laofan.iantha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
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
    private Float total;

    @NotNull
    @Column(name = "shipping", nullable = false)
    private Float shipping;

    @NotNull
    @Column(name = "payable", nullable = false)
    private Float payable;

    @NotNull
    @Column(name = "tax", nullable = false)
    private Float tax;

    @NotNull
    @Column(name = "discount", nullable = false)
    private Float discount;

    @NotNull
    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    @NotNull
    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "orders" }, allowSetters = true)
    private Refund refund;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "order", "provider" }, allowSetters = true)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "order" }, allowSetters = true)
    private Set<DiscountCode> discountCodes = new HashSet<>();

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

    public Float getTotal() {
        return this.total;
    }

    public Order total(Float total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Float total) {
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

    public Float getPayable() {
        return this.payable;
    }

    public Order payable(Float payable) {
        this.setPayable(payable);
        return this;
    }

    public void setPayable(Float payable) {
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

    public Float getDiscount() {
        return this.discount;
    }

    public Order discount(Float discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Boolean getIsPaid() {
        return this.isPaid;
    }

    public Order isPaid(Boolean isPaid) {
        this.setIsPaid(isPaid);
        return this;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Boolean getIsCompleted() {
        return this.isCompleted;
    }

    public Order isCompleted(Boolean isCompleted) {
        this.setIsCompleted(isCompleted);
        return this;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
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

    public Refund getRefund() {
        return this.refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public Order refund(Refund refund) {
        this.setRefund(refund);
        return this;
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
            ", isPaid='" + getIsPaid() + "'" +
            ", isCompleted='" + getIsCompleted() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
