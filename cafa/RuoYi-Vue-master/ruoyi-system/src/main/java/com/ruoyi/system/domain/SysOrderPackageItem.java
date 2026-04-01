package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SysOrderPackageItem {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long orderId;

    private Long packageId;

    private String packageName;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal subtotal;

    private Date createTime;

    private List<PackageItemConfig> itemConfigs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<PackageItemConfig> getItemConfigs() {
        return itemConfigs;
    }

    public void setItemConfigs(List<PackageItemConfig> itemConfigs) {
        this.itemConfigs = itemConfigs;
    }
}