package com.laofan.iantha.service.dto;

import com.laofan.iantha.domain.enumeration.LabelCate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.laofan.iantha.domain.BabyLabel} entity.
 */
@Schema(description = "商品标签")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BabyLabelDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "标签名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotNull
    @Schema(description = "标签分类", requiredMode = Schema.RequiredMode.REQUIRED)
    private LabelCate labelCate;

    @NotNull
    @Schema(description = "标签编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String labelCode;

    @NotNull
    @Schema(description = "标签附加属性", requiredMode = Schema.RequiredMode.REQUIRED)
    private String labelAttr;

    @NotNull
    @Schema(description = "创建者ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String identify;

    @Schema(description = "规则说明富文本")
    @Lob
    private String ruleReadme;

    @Schema(description = "规则说明富文本")
    @Lob
    private String ruleExpression;

    private ProductDTO products;

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

    public LabelCate getLabelCate() {
        return labelCate;
    }

    public void setLabelCate(LabelCate labelCate) {
        this.labelCate = labelCate;
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    public String getLabelAttr() {
        return labelAttr;
    }

    public void setLabelAttr(String labelAttr) {
        this.labelAttr = labelAttr;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getRuleReadme() {
        return ruleReadme;
    }

    public void setRuleReadme(String ruleReadme) {
        this.ruleReadme = ruleReadme;
    }

    public String getRuleExpression() {
        return ruleExpression;
    }

    public void setRuleExpression(String ruleExpression) {
        this.ruleExpression = ruleExpression;
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
        if (!(o instanceof BabyLabelDTO)) {
            return false;
        }

        BabyLabelDTO babyLabelDTO = (BabyLabelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, babyLabelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BabyLabelDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", labelCate='" + getLabelCate() + "'" +
            ", labelCode='" + getLabelCode() + "'" +
            ", labelAttr='" + getLabelAttr() + "'" +
            ", identify='" + getIdentify() + "'" +
            ", ruleReadme='" + getRuleReadme() + "'" +
            ", ruleExpression='" + getRuleExpression() + "'" +
            ", products=" + getProducts() +
            "}";
    }
}
