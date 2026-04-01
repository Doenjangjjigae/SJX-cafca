package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysOrderItem;

import java.util.List;

public interface ISysOrderItemService {
    /**
     * 查询订单商品列表
     *
     * @param orderItem 订单商品信息
     * @return 订单商品集合
     */
    public List<SysOrderItem> selectOrderItemList(SysOrderItem orderItem);

    /**
     * 通过ID查询订单商品
     *
     * @param id ID
     * @return 订单商品信息
     */
    public SysOrderItem selectOrderItemById(Long id);

    /**
     * 通过订单ID查询订单商品列表
     *
     * @param orderId 订单ID
     * @return 订单商品列表
     */
    public List<SysOrderItem> selectOrderItemByOrderId(Long orderId);

    /**
     * 新增订单商品
     *
     * @param orderItem 订单商品信息
     * @return 结果
     */
    public int insertOrderItem(SysOrderItem orderItem);

    /**
     * 修改订单商品
     *
     * @param orderItem 订单商品信息
     * @return 结果
     */
    public int updateOrderItem(SysOrderItem orderItem);

    /**
     * 删除订单商品
     *
     * @param id ID
     * @return 结果
     */
    public int deleteOrderItemById(Long id);

    /**
     * 批量删除订单商品
     *
     * @param ids 需要删除的ID
     * @return 结果
     */
    public int deleteOrderItemByIds(Long[] ids);
}
