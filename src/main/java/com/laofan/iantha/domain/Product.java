package com.laofan.iantha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laofan.iantha.domain.enumeration.ProdStatus;
import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 商品编码
     */
    @NotNull
    @Column(name = "trans_code", nullable = false)
    private String transCode;

    @Column(name = "description")
    private String description;

    /**
     * 默认图
     */
    @Schema(description = "默认图")
    @Column(name = "images",columnDefinition = "text[]")
    @Type(StringArrayType.class)
    private String[] images;

    @Column(name = "keywords",columnDefinition = "text[]")
    @Type(StringArrayType.class)
    private String[] keywords;

    @Lob
    @Column(name = "metadata")
    private String metadata;

    /**
     * 成本价
     */
    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "guide_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal guidePrice;

    /**
     * 平台销售单价
     */
    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    /**
     * 画线价
     */
    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "show_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal showPrice;

    @NotNull
    @Column(name = "discount", nullable = false)
    private Integer discount;

    /**
     * 折扣
     */
    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock;

    /**
     * 库存
     */
    @NotNull
    @Column(name = "is_physical", nullable = false)
    private Boolean isPhysical;

    /**
     * 是否物理商品
     */
    @NotNull
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    /**
     * 是否可用
     */
    @NotNull
    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured;

    /**
     * 是否物色
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProdStatus status;

    /**
     * 销量,此处简单维护,期望接入进入销存系统
     */
    @Column(name = "sell_count")
    private Integer sellCount;

    /**
     * 库存,此处简单维护,期望接入进入销存系统
     */
    @Column(name = "stock_count")
    private Integer stockCount;

    /**
     * 上架状态
     */
    @Column(name = "shelves_status")
    private Boolean shelvesStatus;

    /**
     * 上架时间
     */
    @Column(name = "shelves_date")
    private Instant shelvesDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private Category categories;

    @JsonIgnoreProperties(value = { "product", "orderItem" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "product")
    private OrderItem orderItem;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "products")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private Set<BabyLabel> labels = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "products")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private Set<BabySpec> specs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Product title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTransCode() {
        return this.transCode;
    }

    public Product transCode(String transCode) {
        this.setTransCode(transCode);
        return this;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getImages() {
        return this.images;
    }

    public Product images(String[] images) {
        this.setImages(images);
        return this;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String[] getKeywords() {
        return this.keywords;
    }

    public Product keywords(String[] keywords) {
        this.setKeywords(keywords);
        return this;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String getMetadata() {
        return this.metadata;
    }

    public Product metadata(String metadata) {
        this.setMetadata(metadata);
        return this;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public BigDecimal getGuidePrice() {
        return this.guidePrice;
    }

    public Product guidePrice(BigDecimal guidePrice) {
        this.setGuidePrice(guidePrice);
        return this;
    }

    public void setGuidePrice(BigDecimal guidePrice) {
        this.guidePrice = guidePrice;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Product price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getShowPrice() {
        return this.showPrice;
    }

    public Product showPrice(BigDecimal showPrice) {
        this.setShowPrice(showPrice);
        return this;
    }

    public void setShowPrice(BigDecimal showPrice) {
        this.showPrice = showPrice;
    }

    public Integer getDiscount() {
        return this.discount;
    }

    public Product discount(Integer discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getStock() {
        return this.stock;
    }

    public Product stock(Integer stock) {
        this.setStock(stock);
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getIsPhysical() {
        return this.isPhysical;
    }

    public Product isPhysical(Boolean isPhysical) {
        this.setIsPhysical(isPhysical);
        return this;
    }

    public void setIsPhysical(Boolean isPhysical) {
        this.isPhysical = isPhysical;
    }

    public Boolean getIsAvailable() {
        return this.isAvailable;
    }

    public Product isAvailable(Boolean isAvailable) {
        this.setIsAvailable(isAvailable);
        return this;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean getIsFeatured() {
        return this.isFeatured;
    }

    public Product isFeatured(Boolean isFeatured) {
        this.setIsFeatured(isFeatured);
        return this;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public ProdStatus getStatus() {
        return this.status;
    }

    public Product status(ProdStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ProdStatus status) {
        this.status = status;
    }

    public Integer getSellCount() {
        return this.sellCount;
    }

    public Product sellCount(Integer sellCount) {
        this.setSellCount(sellCount);
        return this;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public Integer getStockCount() {
        return this.stockCount;
    }

    public Product stockCount(Integer stockCount) {
        this.setStockCount(stockCount);
        return this;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Boolean getShelvesStatus() {
        return this.shelvesStatus;
    }

    public Product shelvesStatus(Boolean shelvesStatus) {
        this.setShelvesStatus(shelvesStatus);
        return this;
    }

    public void setShelvesStatus(Boolean shelvesStatus) {
        this.shelvesStatus = shelvesStatus;
    }

    public Instant getShelvesDate() {
        return this.shelvesDate;
    }

    public Product shelvesDate(Instant shelvesDate) {
        this.setShelvesDate(shelvesDate);
        return this;
    }

    public void setShelvesDate(Instant shelvesDate) {
        this.shelvesDate = shelvesDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Product createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Product updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Product brand(Brand brand) {
        this.setBrand(brand);
        return this;
    }

    public Category getCategories() {
        return this.categories;
    }

    public void setCategories(Category category) {
        this.categories = category;
    }

    public Product categories(Category category) {
        this.setCategories(category);
        return this;
    }

    public OrderItem getOrderItem() {
        return this.orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        if (this.orderItem != null) {
            this.orderItem.setProduct(null);
        }
        if (orderItem != null) {
            orderItem.setProduct(this);
        }
        this.orderItem = orderItem;
    }

    public Product orderItem(OrderItem orderItem) {
        this.setOrderItem(orderItem);
        return this;
    }

    public Set<BabyLabel> getLabels() {
        return this.labels;
    }

    public void setLabels(Set<BabyLabel> babyLabels) {
        if (this.labels != null) {
            this.labels.forEach(i -> i.setProducts(null));
        }
        if (babyLabels != null) {
            babyLabels.forEach(i -> i.setProducts(this));
        }
        this.labels = babyLabels;
    }

    public Product labels(Set<BabyLabel> babyLabels) {
        this.setLabels(babyLabels);
        return this;
    }

    public Product addLabel(BabyLabel babyLabel) {
        this.labels.add(babyLabel);
        babyLabel.setProducts(this);
        return this;
    }

    public Product removeLabel(BabyLabel babyLabel) {
        this.labels.remove(babyLabel);
        babyLabel.setProducts(null);
        return this;
    }

    public Set<CartItem> getCartItems() {
        return this.cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        if (this.cartItems != null) {
            this.cartItems.forEach(i -> i.setProduct(null));
        }
        if (cartItems != null) {
            cartItems.forEach(i -> i.setProduct(this));
        }
        this.cartItems = cartItems;
    }

    public Product cartItems(Set<CartItem> cartItems) {
        this.setCartItems(cartItems);
        return this;
    }

    public Product addCartItems(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setProduct(this);
        return this;
    }

    public Product removeCartItems(CartItem cartItem) {
        this.cartItems.remove(cartItem);
        cartItem.setProduct(null);
        return this;
    }

    public Set<BabySpec> getSpecs() {
        return this.specs;
    }

    public void setSpecs(Set<BabySpec> babySpecs) {
        if (this.specs != null) {
            this.specs.forEach(i -> i.setProducts(null));
        }
        if (babySpecs != null) {
            babySpecs.forEach(i -> i.setProducts(this));
        }
        this.specs = babySpecs;
    }

    public Product specs(Set<BabySpec> babySpecs) {
        this.setSpecs(babySpecs);
        return this;
    }

    public Product addSpec(BabySpec babySpec) {
        this.specs.add(babySpec);
        babySpec.setProducts(this);
        return this;
    }

    public Product removeSpec(BabySpec babySpec) {
        this.specs.remove(babySpec);
        babySpec.setProducts(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
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
            "}";
    }
}
