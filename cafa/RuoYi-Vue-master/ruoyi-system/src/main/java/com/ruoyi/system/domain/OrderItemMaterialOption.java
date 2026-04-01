package com.ruoyi.system.domain;

import java.math.BigDecimal;

public class OrderItemMaterialOption {
    private static final long serialVersionUID = 1L;

    private Long productMaterialId;

    private Long materialId;

    private String materialName;

    private String optionLabel;

    private BigDecimal quantity;

    public Long getProductMaterialId() {
        return productMaterialId;
    }

    public void setProductMaterialId(Long productMaterialId) {
        this.productMaterialId = productMaterialId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getOptionLabel() {
        return optionLabel;
    }

    public void setOptionLabel(String optionLabel) {
        this.optionLabel = optionLabel;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
