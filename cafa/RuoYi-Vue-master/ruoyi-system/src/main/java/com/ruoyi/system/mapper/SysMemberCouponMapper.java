package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysMemberCoupon;

import java.util.List;

public interface SysMemberCouponMapper {
    /**
     * 查询会员优惠券列表
     *
     * @param memberCoupon 会员优惠券信息
     * @return 会员优惠券集合
     */
    public List<SysMemberCoupon> selectMemberCouponList(SysMemberCoupon memberCoupon);

    /**
     * 通过ID查询会员优惠券
     *
     * @param id ID
     * @return 会员优惠券信息
     */
    public SysMemberCoupon selectMemberCouponById(Long id);

    /**
     * 通过会员ID查询会员优惠券
     *
     * @param memberId 会员ID
     * @return 会员优惠券集合
     */
    public List<SysMemberCoupon> selectMemberCouponByMemberId(Long memberId);

    /**
     * 新增会员优惠券
     *
     * @param memberCoupon 会员优惠券信息
     * @return 结果
     */
    public int insertMemberCoupon(SysMemberCoupon memberCoupon);

    /**
     * 修改会员优惠券
     *
     * @param memberCoupon 会员优惠券信息
     * @return 结果
     */
    public int updateMemberCoupon(SysMemberCoupon memberCoupon);

    /**
     * 删除会员优惠券
     *
     * @param id ID
     * @return 结果
     */
    public int deleteMemberCouponById(Long id);

    /**
     * 批量删除会员优惠券
     *
     * @param ids 需要删除的ID
     * @return 结果
     */
    public int deleteMemberCouponByIds(Long[] ids);
}
