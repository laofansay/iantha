package com.laofan.iantha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 购物项目
 */
@Schema(description = "购物项目")
@Entity
@Table(name = "cart_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "cid", nullable = false)
    private String cid;

    @NotNull
    @Column(name = "prod_id", nullable = false)
    private String prodId;

    @NotNull
    @Column(name = "count", nullable = false)
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "brand", "categories", "labels", "cartItems", "specs" }, allowSetters = true)
    private Product product;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "items")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "items" }, allowSetters = true)
    private Set<Cart> carts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CartItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCid() {
        return this.cid;
    }

    public CartItem cid(String cid) {
        this.setCid(cid);
        return this;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getProdId() {
        return this.prodId;
    }

    public CartItem prodId(String prodId) {
        this.setProdId(prodId);
        return this;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Integer getCount() {
        return this.count;
    }

    public CartItem count(Integer count) {
        this.setCount(count);
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItem product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Set<Cart> getCarts() {
        return this.carts;
    }

    public void setCarts(Set<Cart> carts) {
        if (this.carts != null) {
            this.carts.forEach(i -> i.setItems(null));
        }
        if (carts != null) {
            carts.forEach(i -> i.setItems(this));
        }
        this.carts = carts;
    }

    public CartItem carts(Set<Cart> carts) {
        this.setCarts(carts);
        return this;
    }

    public CartItem addCart(Cart cart) {
        this.carts.add(cart);
        cart.setItems(this);
        return this;
    }

    public CartItem removeCart(Cart cart) {
        this.carts.remove(cart);
        cart.setItems(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartItem)) {
            return false;
        }
        return getId() != null && getId().equals(((CartItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CartItem{" +
            "id=" + getId() +
            ", cid='" + getCid() + "'" +
            ", prodId='" + getProdId() + "'" +
            ", count=" + getCount() +
            "}";
    }
}
