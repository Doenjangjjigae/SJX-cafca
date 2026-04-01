package com.ruoyi.system.domain;

import java.io.Serializable;
import java.util.List;

public class PackageItemConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long productId;

    private String productName;

    private Integer quantity;

    private List<OrderItemConfigOption> configOptionList;

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

    public List<OrderItemConfigOption> getConfigOptionList() {
        return configOptionList;
    }

    public void setConfigOptionList(List<OrderItemConfigOption> configOptionList) {
        this.configOptionList = configOptionList;
    }
}
