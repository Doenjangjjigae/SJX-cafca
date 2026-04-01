package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

public class SysProduct extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long productId;

    private String productName;

    private String productCode;

    private BigDecimal price;

    private String category;

    private String status;

    private String remark;

    private List<SysProductMaterial> materials;

    private List<SysProductConfigGroup> configGroups;

    private List<SysProductDefaultMaterial> defaultMaterials;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public List<SysProductMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<SysProductMaterial> materials) {
        this.materials = materials;
    }

    public List<SysProductConfigGroup> getConfigGroups() {
        return configGroups;
    }

    public void setConfigGroups(List<SysProductConfigGroup> configGroups) {
        this.configGroups = configGroups;
    }

    public List<SysProductDefaultMaterial> getDefaultMaterials() {
        return defaultMaterials;
    }

    public void setDefaultMaterials(List<SysProductDefaultMaterial> defaultMaterials) {
        this.defaultMaterials = defaultMaterials;
    }
}
