package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public class SysOrderCoupon {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 订单ID */
    private Long orderId;

    /** 优惠券ID */
    private Long couponId;

    /** 优惠金额 */
    private BigDecimal discountAmount;

    /** 创建时间 */
    private Date createTime;

    /** 优惠券信息 */
    private SysCoupon coupon;

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

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SysCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(SysCoupon coupon) {
        this.coupon = coupon;
    }
}
