package com.laofan.iantha.domain;

import com.laofan.iantha.domain.enumeration.FavCate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Wishlist.
 */
@Entity
@Table(name = "wishlist")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wishlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 标识码
     */
    @Schema(description = "标识码", required = true)
    @Column(name = "identify", nullable = false)
    private String identify;

    /**
     * 标识码
     */
    @Schema(description = "标识码", required = true)
    @NotNull
    @Column(name = "biz_code", nullable = false)
    private String bizCode;

    /**
     * 收藏内容的描述信息
     */
    @Schema(description = "收藏内容的描述信息", required = true)
    @NotNull
    @Column(name = "biz_desc", nullable = false)
    private String bizDesc;

    /**
     * icon
     */
    @Schema(description = "icon", required = true)
    @NotNull
    @Column(name = "biz_icon", nullable = false)
    private String bizIcon;

    /**
     * 收藏标题
     */
    @Schema(description = "收藏标题", required = true)
    @NotNull
    @Column(name = "biz_title", nullable = false)
    private String bizTitle;

    /**
     * 链接
     */
    @Schema(description = "链接")
    @Column(name = "link")
    private String link;

    /**
     * 收藏类型
     */
    @Schema(description = "收藏类型", required = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fav_cate", nullable = false)
    private FavCate favCate;

    @Column(name = "created_date")
    private Instant createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wishlist id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentify() {
        return this.identify;
    }

    public Wishlist identify(String identify) {
        this.setIdentify(identify);
        return this;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public Wishlist bizCode(String bizCode) {
        this.setBizCode(bizCode);
        return this;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizDesc() {
        return this.bizDesc;
    }

    public Wishlist bizDesc(String bizDesc) {
        this.setBizDesc(bizDesc);
        return this;
    }

    public void setBizDesc(String bizDesc) {
        this.bizDesc = bizDesc;
    }

    public String getBizIcon() {
        return this.bizIcon;
    }

    public Wishlist bizIcon(String bizIcon) {
        this.setBizIcon(bizIcon);
        return this;
    }

    public void setBizIcon(String bizIcon) {
        this.bizIcon = bizIcon;
    }

    public String getBizTitle() {
        return this.bizTitle;
    }

    public Wishlist bizTitle(String bizTitle) {
        this.setBizTitle(bizTitle);
        return this;
    }

    public void setBizTitle(String bizTitle) {
        this.bizTitle = bizTitle;
    }

    public String getLink() {
        return this.link;
    }

    public Wishlist link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public FavCate getFavCate() {
        return this.favCate;
    }

    public Wishlist favCate(FavCate favCate) {
        this.setFavCate(favCate);
        return this;
    }

    public void setFavCate(FavCate favCate) {
        this.favCate = favCate;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Wishlist createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wishlist)) {
            return false;
        }
        return getId() != null && getId().equals(((Wishlist) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wishlist{" +
            "id=" + getId() +
            ", identify='" + getIdentify() + "'" +
            ", bizCode='" + getBizCode() + "'" +
            ", bizDesc='" + getBizDesc() + "'" +
            ", bizIcon='" + getBizIcon() + "'" +
            ", bizTitle='" + getBizTitle() + "'" +
            ", link='" + getLink() + "'" +
            ", favCate='" + getFavCate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
