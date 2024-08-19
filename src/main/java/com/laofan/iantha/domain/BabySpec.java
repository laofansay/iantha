package com.laofan.iantha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laofan.iantha.domain.enumeration.ProdStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 商品规模
 */
@Schema(description = "商品规模")
@Entity
@Table(name = "baby_spec")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BabySpec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 规格编码
     */
    @Schema(description = "规格编码", required = true)
    @NotNull
    @Column(name = "spec_code", nullable = false)
    private String specCode;

    /**
     * 规规格标题
     */
    @Schema(description = "规规格标题", required = true)
    @NotNull
    @Column(name = "spec_title", nullable = false)
    private String specTitle;

    /**
     * 规模规格描述
     */
    @Schema(description = "规模规格描述", required = true)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * 规规格标题
     */
    @Schema(description = "规规格标题", required = true)
    @NotNull
    @Column(name = "spec_quantity", nullable = false)
    private Integer specQuantity;

    /**
     * 成本价
     */
    @Schema(description = "成本价", required = true)
    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "guide_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal guidePrice;

    /**
     * 单价
     */
    @Schema(description = "单价", required = true)
    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "spec_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal specPrice;

    /**
     * 画线价
     */
    @Schema(description = "画线价", required = true)
    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "show_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal showPrice;

    /**
     * 规格状态
     */
    @Schema(description = "规格状态", required = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "spec_status", nullable = false)
    private ProdStatus specStatus;

    /**
     * 规格图片
     */
    @Schema(description = "规格图片", required = true)
    @NotNull
    @Column(name = "images", nullable = false)
    private String images;

    /**
     * 销量,此处简单维护,期望接入进入销存系统
     */
    @Schema(description = "销量,此处简单维护,期望接入进入销存系统")
    @Column(name = "sell_count")
    private Integer sellCount;

    /**
     * 库存,此处简单维护,期望接入进入销存系统
     */
    @Schema(description = "库存,此处简单维护,期望接入进入销存系统")
    @Column(name = "stock_count")
    private Integer stockCount;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "brand", "categories", "labels", "cartItems", "specs" }, allowSetters = true)
    private Product products;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BabySpec id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecCode() {
        return this.specCode;
    }

    public BabySpec specCode(String specCode) {
        this.setSpecCode(specCode);
        return this;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public String getSpecTitle() {
        return this.specTitle;
    }

    public BabySpec specTitle(String specTitle) {
        this.setSpecTitle(specTitle);
        return this;
    }

    public void setSpecTitle(String specTitle) {
        this.specTitle = specTitle;
    }

    public String getDescription() {
        return this.description;
    }

    public BabySpec description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSpecQuantity() {
        return this.specQuantity;
    }

    public BabySpec specQuantity(Integer specQuantity) {
        this.setSpecQuantity(specQuantity);
        return this;
    }

    public void setSpecQuantity(Integer specQuantity) {
        this.specQuantity = specQuantity;
    }

    public BigDecimal getGuidePrice() {
        return this.guidePrice;
    }

    public BabySpec guidePrice(BigDecimal guidePrice) {
        this.setGuidePrice(guidePrice);
        return this;
    }

    public void setGuidePrice(BigDecimal guidePrice) {
        this.guidePrice = guidePrice;
    }

    public BigDecimal getSpecPrice() {
        return this.specPrice;
    }

    public BabySpec specPrice(BigDecimal specPrice) {
        this.setSpecPrice(specPrice);
        return this;
    }

    public void setSpecPrice(BigDecimal specPrice) {
        this.specPrice = specPrice;
    }

    public BigDecimal getShowPrice() {
        return this.showPrice;
    }

    public BabySpec showPrice(BigDecimal showPrice) {
        this.setShowPrice(showPrice);
        return this;
    }

    public void setShowPrice(BigDecimal showPrice) {
        this.showPrice = showPrice;
    }

    public ProdStatus getSpecStatus() {
        return this.specStatus;
    }

    public BabySpec specStatus(ProdStatus specStatus) {
        this.setSpecStatus(specStatus);
        return this;
    }

    public void setSpecStatus(ProdStatus specStatus) {
        this.specStatus = specStatus;
    }

    public String getImages() {
        return this.images;
    }

    public BabySpec images(String images) {
        this.setImages(images);
        return this;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getSellCount() {
        return this.sellCount;
    }

    public BabySpec sellCount(Integer sellCount) {
        this.setSellCount(sellCount);
        return this;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public Integer getStockCount() {
        return this.stockCount;
    }

    public BabySpec stockCount(Integer stockCount) {
        this.setStockCount(stockCount);
        return this;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public BabySpec createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public BabySpec updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Product getProducts() {
        return this.products;
    }

    public void setProducts(Product product) {
        this.products = product;
    }

    public BabySpec products(Product product) {
        this.setProducts(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BabySpec)) {
            return false;
        }
        return getId() != null && getId().equals(((BabySpec) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BabySpec{" +
            "id=" + getId() +
            ", specCode='" + getSpecCode() + "'" +
            ", specTitle='" + getSpecTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", specQuantity=" + getSpecQuantity() +
            ", guidePrice=" + getGuidePrice() +
            ", specPrice=" + getSpecPrice() +
            ", showPrice=" + getShowPrice() +
            ", specStatus='" + getSpecStatus() + "'" +
            ", images='" + getImages() + "'" +
            ", sellCount=" + getSellCount() +
            ", stockCount=" + getStockCount() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
