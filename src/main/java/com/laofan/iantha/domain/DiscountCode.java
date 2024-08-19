package com.laofan.iantha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DiscountCode.
 */
@Entity
@Table(name = "discount_code")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiscountCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "percent", nullable = false)
    private Integer percent;

    @NotNull
    @Column(name = "max_discount_amount", nullable = false)
    private Float maxDiscountAmount;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "refund", "payments", "discountCodes" }, allowSetters = true)
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DiscountCode id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public DiscountCode code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStock() {
        return this.stock;
    }

    public DiscountCode stock(Integer stock) {
        this.setStock(stock);
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return this.description;
    }

    public DiscountCode description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPercent() {
        return this.percent;
    }

    public DiscountCode percent(Integer percent) {
        this.setPercent(percent);
        return this;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Float getMaxDiscountAmount() {
        return this.maxDiscountAmount;
    }

    public DiscountCode maxDiscountAmount(Float maxDiscountAmount) {
        this.setMaxDiscountAmount(maxDiscountAmount);
        return this;
    }

    public void setMaxDiscountAmount(Float maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public DiscountCode startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public DiscountCode endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public DiscountCode createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public DiscountCode order(Order order) {
        this.setOrder(order);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscountCode)) {
            return false;
        }
        return getId() != null && getId().equals(((DiscountCode) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscountCode{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", stock=" + getStock() +
            ", description='" + getDescription() + "'" +
            ", percent=" + getPercent() +
            ", maxDiscountAmount=" + getMaxDiscountAmount() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
