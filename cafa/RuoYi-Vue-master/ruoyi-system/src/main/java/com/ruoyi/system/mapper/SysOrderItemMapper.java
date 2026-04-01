package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysOrderItem;

import java.util.List;

public interface SysOrderItemMapper {


    List<SysOrderItem> selectOrderItemList(SysOrderItem orderItem);

    /**
     * 通过订单ID查询订单商品关联
     *
     * @param orderId 订单ID
     * @return 订单商品关联列表
     */
    SysOrderItem selectOrderItemByOrderId(Long orderId);

    /**
     * 新增订单商品关联
     *
     * @param orderItem 订单商品关联信息
     * @return 结果
     */
    int insertOrderItem(SysOrderItem orderItem);

    /**
     * 删除订单商品关联
     *
     * @param orderId 订单ID
     * @return 结果
     */
    int deleteOrderItemByOrderId(Long orderId);

    /**
     * 根据ID修改订单商品关联
     *
     * @param orderItem 订单商品关联信息
     * @return 结果
     */
    int updateOrderItemById(SysOrderItem orderItem);

    /**
     * 批量删除订单商品关联
     *
     * @param ids 需要删除的订单商品关联ID
     * @return 结果
     */
    int deleteOrderItemByIds(Long[] ids);
}