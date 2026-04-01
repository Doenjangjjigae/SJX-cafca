package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysCoupon;
import com.ruoyi.system.domain.SysMember;
import com.ruoyi.system.domain.SysMemberCoupon;
import com.ruoyi.system.mapper.SysCouponMapper;
import com.ruoyi.system.mapper.SysMemberMapper;
import com.ruoyi.system.mapper.SysMemberCouponMapper;
import com.ruoyi.system.service.ISysCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysCouponServiceImpl implements ISysCouponService {
    @Autowired
    private SysCouponMapper sysCouponMapper;

    @Autowired
    private SysMemberMapper memberMapper;

    @Autowired
    private SysMemberCouponMapper memberCouponMapper;

    @Override
    public SysCoupon selectSysCouponById(Long couponId) {
        return sysCouponMapper.selectSysCouponById(couponId);
    }

    @Override
    public List<SysCoupon> selectSysCouponList(SysCoupon sysCoupon) {
        return sysCouponMapper.selectSysCouponList(sysCoupon);
    }

    @Override
    public int insertSysCoupon(SysCoupon sysCoupon) {
        sysCoupon.setCreateBy(SecurityUtils.getUsername());
        sysCoupon.setCreateTime(new Date());
        sysCoupon.setUpdateBy(SecurityUtils.getUsername());
        sysCoupon.setUpdateTime(new Date());
        return sysCouponMapper.insertSysCoupon(sysCoupon);
    }

    @Override
    public int updateSysCoupon(SysCoupon sysCoupon) {
        sysCoupon.setUpdateBy(SecurityUtils.getUsername());
        sysCoupon.setUpdateTime(new Date());
        return sysCouponMapper.updateSysCoupon(sysCoupon);
    }

    @Override
    public int deleteSysCouponById(Long couponId) {
        return sysCouponMapper.deleteSysCouponById(couponId);
    }

    @Override
    public int deleteSysCouponByIds(Long[] couponIds) {
        return sysCouponMapper.deleteSysCouponByIds(couponIds);
    }

    @Override
    public int publishSysCoupon(Long[] couponIds, String status) {
        return sysCouponMapper.updateSysCouponStatus(couponIds, status);
    }

    @Override
    @Transactional
    public int issueCouponToAllMembers(Long couponId) {
        SysCoupon coupon = sysCouponMapper.selectSysCouponById(couponId);
        if (coupon == null) {
            return 0;
        }

        List<SysMember> members = memberMapper.selectSysMemberList(new SysMember());
        if (members == null || members.isEmpty()) {
            return 0;
        }

        int count = 0;
        Date now = new Date();
        for (SysMember member : members) {
            SysMemberCoupon existingCoupon = new SysMemberCoupon();
            existingCoupon.setMemberId(member.getMemberId());
            existingCoupon.setCouponId(couponId);
            List<SysMemberCoupon> existingList = memberCouponMapper.selectMemberCouponList(existingCoupon);
            if (existingList != null && !existingList.isEmpty()) {
                continue;
            }

            SysMemberCoupon memberCoupon = new SysMemberCoupon();
            memberCoupon.setMemberId(member.getMemberId());
            memberCoupon.setCouponId(couponId);
            memberCoupon.setReceiveTime(now);
            memberCoupon.setUseStatus("0");
            memberCouponMapper.insertMemberCoupon(memberCoupon);
            count++;
        }
        return count;
    }

    @Override
    @Transactional
    public int issueCouponsToAllMembers(Long[] couponIds) {
        int totalCount = 0;
        for (Long couponId : couponIds) {
            totalCount += issueCouponToAllMembers(couponId);
        }
        return totalCount;
    }
}