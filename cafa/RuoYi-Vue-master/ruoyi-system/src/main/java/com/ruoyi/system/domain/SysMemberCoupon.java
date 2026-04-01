package com.ruoyi.system.domain;

import java.util.Date;

public class SysMemberCoupon {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 会员ID */
    private Long memberId;

    /** 优惠券ID */
    private Long couponId;

    /** 领取时间 */
    private Date receiveTime;

    /** 使用状态（0-未使用，1-已使用，2-已过期） */
    private String useStatus;

    /** 使用时间 */
    private Date useTime;

    /** 优惠券信息 */
    private SysCoupon coupon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public SysCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(SysCoupon coupon) {
        this.coupon = coupon;
    }
}
