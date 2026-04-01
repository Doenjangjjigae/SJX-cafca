package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

public class SysPackage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long packageId;

    private String packageName;

    private String packageCode;

    private BigDecimal price;

    private String status;

    private String remark;

    private List<SysPackageItem> items;

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<SysPackageItem> getItems() {
        return items;
    }

    public void setItems(List<SysPackageItem> items) {
        this.items = items;
    }
}