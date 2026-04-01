package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import java.util.List;

public class SysProductConfigGroup extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long configGroupId;

    private Long productId;

    private String groupName;

    private String groupType;

    private String isRequired;

    private String isMultiple;

    private Integer sortOrder;

    private List<SysProductConfigOption> options;

    public Long getConfigGroupId() {
        return configGroupId;
    }

    public void setConfigGroupId(Long configGroupId) {
        this.configGroupId = configGroupId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public String getIsMultiple() {
        return isMultiple;
    }

    public void setIsMultiple(String isMultiple) {
        this.isMultiple = isMultiple;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<SysProductConfigOption> getOptions() {
        return options;
    }

    public void setOptions(List<SysProductConfigOption> options) {
        this.options = options;
    }
}
