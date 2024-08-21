package com.laofan.iantha.service.dto;

import com.laofan.iantha.domain.enumeration.ProdStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.laofan.iantha.domain.Product} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Schema(description = "商品编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String transCode;

    private String description;

    @Schema(description = "默认图")
    private String[] images;

    private String[] keywords;

    @Lob
    private String metadata;

    @NotNull
    @DecimalMin(value = "0")
    @Schema(description = "成本价", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal guidePrice;

    @NotNull
    @DecimalMin(value = "0")
    @Schema(description = "平台销售单价", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    @NotNull
    @DecimalMin(value = "0")
    @Schema(description = "画线价", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal showPrice;

    @NotNull
    private Integer discount;

    @NotNull
    @Schema(description = "折扣", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer stock;

    @NotNull
    @Schema(description = "库存", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isPhysical;

    @NotNull
    @Schema(description = "是否物理商品", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isAvailable;

    @NotNull
    @Schema(description = "是否可用", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isFeatured;

    @Schema(description = "是否物色")
    private ProdStatus status;

    @Schema(description = "销量,此处简单维护,期望接入进入销存系统")
    private Integer sellCount;

    @Schema(description = "库存,此处简单维护,期望接入进入销存系统")
    private Integer stockCount;

    @Schema(description = "上架状态")
    private Boolean shelvesStatus;

    @Schema(description = "上架时间")
    private Instant shelvesDate;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    private BrandDTO brand;

    private CategoryDTO categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public BigDecimal getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(BigDecimal guidePrice) {
        this.guidePrice = guidePrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(BigDecimal showPrice) {
        this.showPrice = showPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getIsPhysical() {
        return isPhysical;
    }

    public void setIsPhysical(Boolean isPhysical) {
        this.isPhysical = isPhysical;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public ProdStatus getStatus() {
        return status;
    }

    public void setStatus(ProdStatus status) {
        this.status = status;
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

    public Boolean getShelvesStatus() {
        return shelvesStatus;
    }

    public void setShelvesStatus(Boolean shelvesStatus) {
        this.shelvesStatus = shelvesStatus;
    }

    public Instant getShelvesDate() {
        return shelvesDate;
    }

    public void setShelvesDate(Instant shelvesDate) {
        this.shelvesDate = shelvesDate;
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

    public BrandDTO getBrand() {
        return brand;
    }

    public void setBrand(BrandDTO brand) {
        this.brand = brand;
    }

    public CategoryDTO getCategories() {
        return categories;
    }

    public void setCategories(CategoryDTO categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", transCode='" + getTransCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", images='" + getImages() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", metadata='" + getMetadata() + "'" +
            ", guidePrice=" + getGuidePrice() +
            ", price=" + getPrice() +
            ", showPrice=" + getShowPrice() +
            ", discount=" + getDiscount() +
            ", stock=" + getStock() +
            ", isPhysical='" + getIsPhysical() + "'" +
            ", isAvailable='" + getIsAvailable() + "'" +
            ", isFeatured='" + getIsFeatured() + "'" +
            ", status='" + getStatus() + "'" +
            ", sellCount=" + getSellCount() +
            ", stockCount=" + getStockCount() +
            ", shelvesStatus='" + getShelvesStatus() + "'" +
            ", shelvesDate='" + getShelvesDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", brand=" + getBrand() +
            ", categories=" + getCategories() +
            "}";
    }
}
