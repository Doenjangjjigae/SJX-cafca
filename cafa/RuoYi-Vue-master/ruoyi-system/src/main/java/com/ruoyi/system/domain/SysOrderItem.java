package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SysOrderItem {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long orderId;

    private Long productId;

    private String productName;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal subtotal;

    private Date createTime;

    private String materialOptions;

    private String configOptions;

    private List<OrderItemMaterialOption> selectedOptions;

    private List<OrderItemConfigOption> configOptionList;

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

    public String getMaterialOptions() {
        return materialOptions;
    }

    public void setMaterialOptions(String materialOptions) {
        this.materialOptions = materialOptions;
    }

    public String getConfigOptions() {
        return configOptions;
    }

    public void setConfigOptions(String configOptions) {
        this.configOptions = configOptions;
    }

    public List<OrderItemMaterialOption> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<OrderItemMaterialOption> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public List<OrderItemConfigOption> getConfigOptionList() {
        return configOptionList;
    }

    public void setConfigOptionList(List<OrderItemConfigOption> configOptionList) {
        this.configOptionList = configOptionList;
    }
}
