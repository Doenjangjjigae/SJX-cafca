package com.ruoyi.system.domain;

import java.math.BigDecimal;

public class OrderItemConfigOption {
    private static final long serialVersionUID = 1L;

    private Long configGroupId;

    private String groupName;

    private String groupType;

    private Long configOptionId;

    private String optionName;

    private String optionValue;

    private BigDecimal extraPrice;

    private Integer quantity;

    public Long getConfigGroupId() {
        return configGroupId;
    }

    public void setConfigGroupId(Long configGroupId) {
        this.configGroupId = configGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public Long getConfigOptionId() {
        return configOptionId;
    }

    public void setConfigOptionId(Long configOptionId) {
        this.configOptionId = configOptionId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
