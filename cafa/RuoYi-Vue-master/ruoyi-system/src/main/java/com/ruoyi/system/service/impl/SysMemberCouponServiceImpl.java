package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysMemberCoupon;
import com.ruoyi.system.mapper.SysMemberCouponMapper;
import com.ruoyi.system.service.ISysMemberCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMemberCouponServiceImpl implements ISysMemberCouponService {

    @Autowired
    private SysMemberCouponMapper memberCouponMapper;

    @Override
    public List<SysMemberCoupon> selectMemberCouponList(SysMemberCoupon memberCoupon) {
        return memberCouponMapper.selectMemberCouponList(memberCoupon);
    }

    @Override
    public SysMemberCoupon selectMemberCouponById(Long id) {
        return memberCouponMapper.selectMemberCouponById(id);
    }

    @Override
    public List<SysMemberCoupon> selectMemberCouponByMemberId(Long memberId) {
        return memberCouponMapper.selectMemberCouponByMemberId(memberId);
    }

    @Override
    public int insertMemberCoupon(SysMemberCoupon memberCoupon) {
        return memberCouponMapper.insertMemberCoupon(memberCoupon);
    }

    @Override
    public int updateMemberCoupon(SysMemberCoupon memberCoupon) {
        return memberCouponMapper.updateMemberCoupon(memberCoupon);
    }

    @Override
    public int deleteMemberCouponById(Long id) {
        return memberCouponMapper.deleteMemberCouponById(id);
    }

    @Override
    public int deleteMemberCouponByIds(Long[] ids) {
        return memberCouponMapper.deleteMemberCouponByIds(ids);
    }
}
