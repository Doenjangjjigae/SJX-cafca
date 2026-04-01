package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysCoupon;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 优惠券Mapper接口
 */
public interface SysCouponMapper {
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
     * 批量更新优惠券状态
     * 
     * @param couponIds 优惠券ID数组
     * @param status 状态
     * @return 结果
     */
    public int updateSysCouponStatus(@Param("couponIds") Long[] couponIds, @Param("status") String status);
}