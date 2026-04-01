package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysOrder;
import com.ruoyi.system.mapper.SysOrderMapper;
import com.ruoyi.system.service.ISysOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysOrderServiceImpl implements ISysOrderService {
    @Autowired
    private SysOrderMapper orderMapper;

    @Override
    public List<SysOrder> selectOrderList(SysOrder order) {
        return orderMapper.selectOrderList(order);
    }

    @Override
    public SysOrder selectOrderById(Long orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    @Override
    public int insertOrder(SysOrder order) {
        order.setCreateBy(SecurityUtils.getUsername());
        order.setCreateTime(DateUtils.getNowDate());
        order.setUpdateBy(SecurityUtils.getUsername());
        order.setUpdateTime(DateUtils.getNowDate());
        return orderMapper.insertOrder(order);
    }

    @Override
    public int updateOrder(SysOrder order) {
        order.setUpdateBy(SecurityUtils.getUsername());
        order.setUpdateTime(DateUtils.getNowDate());
        return orderMapper.updateOrder(order);
    }

    @Override
    public int deleteOrderById(Long orderId) {
        return orderMapper.deleteOrderById(orderId);
    }

    @Override
    public int deleteOrderByIds(Long[] orderIds) {
        return orderMapper.deleteOrderByIds(orderIds);
    }
}