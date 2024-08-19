package com.laofan.iantha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laofan.iantha.domain.enumeration.LabelCate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 商品标签
 */
@Schema(description = "商品标签")
@Entity
@Table(name = "baby_label")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BabyLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称", required = true)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 标签分类
     */
    @Schema(description = "标签分类", required = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "label_cate", nullable = false)
    private LabelCate labelCate;

    /**
     * 标签编码
     */
    @Schema(description = "标签编码", required = true)
    @NotNull
    @Column(name = "label_code", nullable = false)
    private String labelCode;

    /**
     * 标签附加属性
     */
    @Schema(description = "标签附加属性", required = true)
    @NotNull
    @Column(name = "label_attr", nullable = false)
    private String labelAttr;

    /**
     * 创建者ID
     */
    @Schema(description = "创建者ID", required = true)
    @NotNull
    @Column(name = "identify", nullable = false)
    private String identify;

    /**
     * 规则说明富文本
     */
    @Schema(description = "规则说明富文本")
    @Lob
    @Column(name = "rule_readme")
    private String ruleReadme;

    /**
     * 规则说明富文本
     */
    @Schema(description = "规则说明富文本")
    @Lob
    @Column(name = "rule_expression")
    private String ruleExpression;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "brand", "categories", "labels", "cartItems", "specs" }, allowSetters = true)
    private Product products;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BabyLabel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public BabyLabel title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LabelCate getLabelCate() {
        return this.labelCate;
    }

    public BabyLabel labelCate(LabelCate labelCate) {
        this.setLabelCate(labelCate);
        return this;
    }

    public void setLabelCate(LabelCate labelCate) {
        this.labelCate = labelCate;
    }

    public String getLabelCode() {
        return this.labelCode;
    }

    public BabyLabel labelCode(String labelCode) {
        this.setLabelCode(labelCode);
        return this;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    public String getLabelAttr() {
        return this.labelAttr;
    }

    public BabyLabel labelAttr(String labelAttr) {
        this.setLabelAttr(labelAttr);
        return this;
    }

    public void setLabelAttr(String labelAttr) {
        this.labelAttr = labelAttr;
    }

    public String getIdentify() {
        return this.identify;
    }

    public BabyLabel identify(String identify) {
        this.setIdentify(identify);
        return this;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getRuleReadme() {
        return this.ruleReadme;
    }

    public BabyLabel ruleReadme(String ruleReadme) {
        this.setRuleReadme(ruleReadme);
        return this;
    }

    public void setRuleReadme(String ruleReadme) {
        this.ruleReadme = ruleReadme;
    }

    public String getRuleExpression() {
        return this.ruleExpression;
    }

    public BabyLabel ruleExpression(String ruleExpression) {
        this.setRuleExpression(ruleExpression);
        return this;
    }

    public void setRuleExpression(String ruleExpression) {
        this.ruleExpression = ruleExpression;
    }

    public Product getProducts() {
        return this.products;
    }

    public void setProducts(Product product) {
        this.products = product;
    }

    public BabyLabel products(Product product) {
        this.setProducts(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BabyLabel)) {
            return false;
        }
        return getId() != null && getId().equals(((BabyLabel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BabyLabel{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", labelCate='" + getLabelCate() + "'" +
            ", labelCode='" + getLabelCode() + "'" +
            ", labelAttr='" + getLabelAttr() + "'" +
            ", identify='" + getIdentify() + "'" +
            ", ruleReadme='" + getRuleReadme() + "'" +
            ", ruleExpression='" + getRuleExpression() + "'" +
            "}";
    }
}
