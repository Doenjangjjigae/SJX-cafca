package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.List;

public class SysProductMaterial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long productId;

    private Long materialId;

    private String materialName;

    private BigDecimal quantity;

    private String isAdjustable;

    private String optionName;

    private String defaultOption;

    private List<SysProductMaterialOption> options;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getIsAdjustable() {
        return isAdjustable;
    }

    public void setIsAdjustable(String isAdjustable) {
        this.isAdjustable = isAdjustable;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getDefaultOption() {
        return defaultOption;
    }

    public void setDefaultOption(String defaultOption) {
        this.defaultOption = defaultOption;
    }

    public List<SysProductMaterialOption> getOptions() {
        return options;
    }

    public void setOptions(List<SysProductMaterialOption> options) {
        this.options = options;
    }
}
