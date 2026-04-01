package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysOrderCoupon;
import com.ruoyi.system.mapper.SysOrderCouponMapper;
import com.ruoyi.system.service.ISysOrderCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysOrderCouponServiceImpl implements ISysOrderCouponService {

    @Autowired
    private SysOrderCouponMapper orderCouponMapper;

    @Override
    public List<SysOrderCoupon> selectOrderCouponList(SysOrderCoupon orderCoupon) {
        return orderCouponMapper.selectOrderCouponList(orderCoupon);
    }
    
    @Override
    public SysOrderCoupon selectOrderCouponById(Long id) {
        return orderCouponMapper.selectOrderCouponById(id);
    }

    @Override
    public List<SysOrderCoupon> selectOrderCouponByOrderId(Long orderId) {
        return orderCouponMapper.selectOrderCouponByOrderId(orderId);
    }

    @Override
    public int insertOrderCoupon(SysOrderCoupon orderCoupon) {
        return orderCouponMapper.insertOrderCoupon(orderCoupon);
    }

    @Override
    public int updateOrderCoupon(SysOrderCoupon orderCoupon) {
        return orderCouponMapper.updateOrderCoupon(orderCoupon);
    }

    @Override
    public int deleteOrderCouponById(Long id) {
        return orderCouponMapper.deleteOrderCouponById(id);
    }

    @Override
    public int deleteOrderCouponByIds(Long[] ids) {
        return orderCouponMapper.deleteOrderCouponByIds(ids);
    }
}
