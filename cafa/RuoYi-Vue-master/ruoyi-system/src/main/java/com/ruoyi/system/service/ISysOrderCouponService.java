package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysOrderCoupon;

import java.util.List;

public interface ISysOrderCouponService {
    /**
     * 查询订单优惠券列表
     *
     * @param orderCoupon 订单优惠券信息
     * @return 订单优惠券集合
     */
    public List<SysOrderCoupon> selectOrderCouponList(SysOrderCoupon orderCoupon);

    /**
     * 通过ID查询订单优惠券
     *
     * @param id ID
     * @return 订单优惠券信息
     */
    public SysOrderCoupon selectOrderCouponById(Long id);

    /**
     * 通过订单ID查询订单优惠券
     *
     * @param orderId 订单ID
     * @return 订单优惠券集合
     */
    public List<SysOrderCoupon> selectOrderCouponByOrderId(Long orderId);

    /**
     * 新增订单优惠券
     *
     * @param orderCoupon 订单优惠券信息
     * @return 结果
     */
    public int insertOrderCoupon(SysOrderCoupon orderCoupon);

    /**
     * 修改订单优惠券
     *
     * @param orderCoupon 订单优惠券信息
     * @return 结果
     */
    public int updateOrderCoupon(SysOrderCoupon orderCoupon);

    /**
     * 删除订单优惠券
     *
     * @param id ID
     * @return 结果
     */
    public int deleteOrderCouponById(Long id);

    /**
     * 批量删除订单优惠券
     *
     * @param ids 需要删除的ID
     * @return 结果
     */
    public int deleteOrderCouponByIds(Long[] ids);
}
