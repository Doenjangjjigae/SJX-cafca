package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.List;

public class SysProductConfigOption extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long configOptionId;

    private Long configGroupId;

    private String optionName;

    private String optionValue;

    private BigDecimal extraPrice;

    private Integer sortOrder;

    private String isDefault;

    private List<SysProductConfigMaterial> materials;

    public Long getConfigOptionId() {
        return configOptionId;
    }

    public void setConfigOptionId(Long configOptionId) {
        this.configOptionId = configOptionId;
    }

    public Long getConfigGroupId() {
        return configGroupId;
    }

    public void setConfigGroupId(Long configGroupId) {
        this.configGroupId = configGroupId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public BigDecimal getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(BigDecimal extraPrice) {
        this.extraPrice = extraPrice;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public List<SysProductConfigMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<SysProductConfigMaterial> materials) {
        this.materials = materials;
    }
}
