package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.List;

public class SysPackageItem {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long packageId;

    private Long productId;

    private String productName;

    private Integer quantity;

    private BigDecimal productPrice;

    private List<SysProductConfigGroup> configGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public List<SysProductConfigGroup> getConfigGroups() {
        return configGroups;
    }

    public void setConfigGroups(List<SysProductConfigGroup> configGroups) {
        this.configGroups = configGroups;
    }
}
