package com.laofan.iantha.service.dto;

import com.laofan.iantha.domain.enumeration.ProdStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.laofan.iantha.domain.BabySpec} entity.
 */
@Schema(description = "商品规模")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BabySpecDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "规格编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String specCode;

    @NotNull
    @Schema(description = "规规格标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String specTitle;

    @NotNull
    @Schema(description = "规模规格描述", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @NotNull
    @Schema(description = "规规格标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer specQuantity;

    @NotNull
    @DecimalMin(value = "0")
    @Schema(description = "成本价", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal guidePrice;

    @NotNull
    @DecimalMin(value = "0")
    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal specPrice;

    @NotNull
    @DecimalMin(value = "0")
    @Schema(description = "画线价", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal showPrice;

    @NotNull
    @Schema(description = "规格状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private ProdStatus specStatus;

    @NotNull
    @Schema(description = "规格图片", requiredMode = Schema.RequiredMode.REQUIRED)
    private String images;

    @Schema(description = "销量,此处简单维护,期望接入进入销存系统")
    private Integer sellCount;

    @Schema(description = "库存,此处简单维护,期望接入进入销存系统")
    private Integer stockCount;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    private ProductDTO products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public String getSpecTitle() {
        return specTitle;
    }

    public void setSpecTitle(String specTitle) {
        this.specTitle = specTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSpecQuantity() {
        return specQuantity;
    }

    public void setSpecQuantity(Integer specQuantity) {
        this.specQuantity = specQuantity;
    }

    public BigDecimal getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(BigDecimal guidePrice) {
        this.guidePrice = guidePrice;
    }

    public BigDecimal getSpecPrice() {
        return specPrice;
    }

    public void setSpecPrice(BigDecimal specPrice) {
        this.specPrice = specPrice;
    }

    public BigDecimal getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(BigDecimal showPrice) {
        this.showPrice = showPrice;
    }

    public ProdStatus getSpecStatus() {
        return specStatus;
    }

    public void setSpecStatus(ProdStatus specStatus) {
        this.specStatus = specStatus;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProductDTO getProducts() {
        return products;
    }

    public void setProducts(ProductDTO products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BabySpecDTO)) {
            return false;
        }

        BabySpecDTO babySpecDTO = (BabySpecDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, babySpecDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BabySpecDTO{" +
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
            ", products=" + getProducts() +
            "}";
    }
}
