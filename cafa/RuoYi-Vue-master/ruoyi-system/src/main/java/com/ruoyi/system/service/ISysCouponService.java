package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysCoupon;
import java.util.List;

/**
 * 优惠券Service接口
 */
public interface ISysCouponService {
    /**
     * 查询优惠券
     * 
     * @param couponId 优惠券ID
     * @return 优惠券
     */
    public SysCoupon selectSysCouponById(Long couponId);

    /**
     * 查询优惠券列表
     * 
     * @param sysCoupon 优惠券
     * @return 优惠券集合
     */
    public List<SysCoupon> selectSysCouponList(SysCoupon sysCoupon);

    /**
     * 新增优惠券
     * 
     * @param sysCoupon 优惠券
     * @return 结果
     */
    public int insertSysCoupon(SysCoupon sysCoupon);

    /**
     * 修改优惠券
     * 
     * @param sysCoupon 优惠券
     * @return 结果
     */
    public int updateSysCoupon(SysCoupon sysCoupon);

    /**
     * 删除优惠券
     * 
     * @param couponId 优惠券ID
     * @return 结果
     */
    public int deleteSysCouponById(Long couponId);

    /**
     * 批量删除优惠券
     * 
     * @param couponIds 需要删除的优惠券ID
     * @return 结果
     */
    public int deleteSysCouponByIds(Long[] couponIds);

    /**
     * 批量发布优惠券
     * 
     * @param couponIds 优惠券ID数组
     * @param status 状态（0-正常，1-禁用）
     * @return 结果
     */
    public int publishSysCoupon(Long[] couponIds, String status);

    /**
     * 发放优惠券给所有会员
     * 
     * @param couponId 优惠券ID
     * @return 结果
     */
    public int issueCouponToAllMembers(Long couponId);

    /**
     * 批量发放优惠券给所有会员
     * 
     * @param couponIds 优惠券ID数组
     * @return 结果
     */
    public int issueCouponsToAllMembers(Long[] couponIds);
}