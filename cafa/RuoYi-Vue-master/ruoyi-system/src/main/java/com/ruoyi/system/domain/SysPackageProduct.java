package com.ruoyi.system.domain;

import java.util.Date;

public class SysPackageProduct {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 套餐ID */
    private Long packageId;

    /** 商品ID */
    private Long productId;

    /** 数量 */
    private Integer quantity;

    /** 创建时间 */
    private Date createTime;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}