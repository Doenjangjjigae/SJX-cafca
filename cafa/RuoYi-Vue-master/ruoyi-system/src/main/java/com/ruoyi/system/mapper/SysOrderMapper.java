package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysOrder;

import java.util.List;

public interface SysOrderMapper {
    /**
     * 查询订单列表
     *
     * @param order 订单信息
     * @return 订单列表
     */
    List<SysOrder> selectOrderList(SysOrder order);

    /**
     * 通过订单ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单信息
     */
    SysOrder selectOrderById(Long orderId);

    /**
     * 新增订单
     *
     * @param order 订单信息
     * @return 结果
     */
    int insertOrder(SysOrder order);

    /**
     * 修改订单
     *
     * @param order 订单信息
     * @return 结果
     */
    int updateOrder(SysOrder order);

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     * @return 结果
     */
    int deleteOrderById(Long orderId);

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单ID
     * @return 结果
     */
    int deleteOrderByIds(Long[] orderIds);
}