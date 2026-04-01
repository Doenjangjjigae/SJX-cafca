package com.ruoyi.system.domain;

import java.math.BigDecimal;

public class SysProductMaterialOption {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long productMaterialId;

    private String optionLabel;

    private BigDecimal quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductMaterialId() {
        return productMaterialId;
    }

    public void setProductMaterialId(Long productMaterialId) {
        this.productMaterialId = productMaterialId;
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
