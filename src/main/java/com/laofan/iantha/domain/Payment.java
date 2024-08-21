package com.laofan.iantha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payment implements Serializable {

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
    @Column(name = "ref_id", nullable = false, unique = true)
    private String refId;

    @Column(name = "card_pan")
    private String cardPan;

    @Column(name = "card_hash")
    private String cardHash;

    @Column(name = "fee")
    private Float fee;

    @NotNull
    @Column(name = "is_successful", nullable = false)
    private Boolean isSuccessful;

    @NotNull
    @Column(name = "payable", nullable = false)
    private Float payable;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "payments", "discountCodes", "orderItems" }, allowSetters = true)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "orders" }, allowSetters = true)
    private PaymentProvider provider;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return this.number;
    }

    public Payment number(Integer number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStatus() {
        return this.status;
    }

    public Payment status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefId() {
        return this.refId;
    }

    public Payment refId(String refId) {
        this.setRefId(refId);
        return this;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getCardPan() {
        return this.cardPan;
    }

    public Payment cardPan(String cardPan) {
        this.setCardPan(cardPan);
        return this;
    }

    public void setCardPan(String cardPan) {
        this.cardPan = cardPan;
    }

    public String getCardHash() {
        return this.cardHash;
    }

    public Payment cardHash(String cardHash) {
        this.setCardHash(cardHash);
        return this;
    }

    public void setCardHash(String cardHash) {
        this.cardHash = cardHash;
    }

    public Float getFee() {
        return this.fee;
    }

    public Payment fee(Float fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public Boolean getIsSuccessful() {
        return this.isSuccessful;
    }

    public Payment isSuccessful(Boolean isSuccessful) {
        this.setIsSuccessful(isSuccessful);
        return this;
    }

    public void setIsSuccessful(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public Float getPayable() {
        return this.payable;
    }

    public Payment payable(Float payable) {
        this.setPayable(payable);
        return this;
    }

    public void setPayable(Float payable) {
        this.payable = payable;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Payment createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Payment updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Payment order(Order order) {
        this.setOrder(order);
        return this;
    }

    public PaymentProvider getProvider() {
        return this.provider;
    }

    public void setProvider(PaymentProvider paymentProvider) {
        this.provider = paymentProvider;
    }

    public Payment provider(PaymentProvider paymentProvider) {
        this.setProvider(paymentProvider);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return getId() != null && getId().equals(((Payment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", status='" + getStatus() + "'" +
            ", refId='" + getRefId() + "'" +
            ", cardPan='" + getCardPan() + "'" +
            ", cardHash='" + getCardHash() + "'" +
            ", fee=" + getFee() +
            ", isSuccessful='" + getIsSuccessful() + "'" +
            ", payable=" + getPayable() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
