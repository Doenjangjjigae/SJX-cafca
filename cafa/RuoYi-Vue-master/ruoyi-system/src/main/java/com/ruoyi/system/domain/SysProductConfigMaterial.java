package com.ruoyi.system.domain;

import java.math.BigDecimal;

public class SysProductConfigMaterial {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long configOptionId;

    private Long materialId;

    private String materialName;

    private BigDecimal quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConfigOptionId() {
        return configOptionId;
    }

    public void setConfigOptionId(Long configOptionId) {
        this.configOptionId = configOptionId;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
