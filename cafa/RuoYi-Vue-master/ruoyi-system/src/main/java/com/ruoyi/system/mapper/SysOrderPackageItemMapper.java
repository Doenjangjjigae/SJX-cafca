package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysOrderPackageItem;

import java.util.List;

public interface SysOrderPackageItemMapper {
    /**
     * 查询订单套餐关联列表
     *
     * @param orderPackageItem 订单套餐关联信息
     * @return 订单套餐关联列表
     */
    List<SysOrderPackageItem> selectOrderPackageItemList(SysOrderPackageItem orderPackageItem);

    /**
     * 通过ID查询订单套餐关联
     *
     * @param id 订单套餐关联ID
     * @return 订单套餐关联信息
     */
    SysOrderPackageItem selectOrderPackageItemById(Long id);

    /**
     * 通过订单ID查询订单套餐关联
     *
     * @param orderId 订单ID
     * @return 订单套餐关联列表
     */
    List<SysOrderPackageItem> selectOrderPackageItemByOrderId(Long orderId);

    /**
     * 新增订单套餐关联
     *
     * @param orderPackageItem 订单套餐关联信息
     * @return 结果
     */
    int insertOrderPackageItem(SysOrderPackageItem orderPackageItem);

    /**
     * 修改订单套餐关联
     *
     * @param orderPackageItem 订单套餐关联信息
     * @return 结果
     */
    int updateOrderPackageItem(SysOrderPackageItem orderPackageItem);

    /**
     * 删除订单套餐关联
     *
     * @param orderId 订单ID
     * @return 结果
     */
    int deleteOrderPackageItemByOrderId(Long orderId);

    /**
     * 根据ID删除订单套餐关联
     *
     * @param id 订单套餐关联ID
     * @return 结果
     */
    int deleteOrderPackageItemById(Long id);

    /**
     * 批量删除订单套餐关联
     *
     * @param ids 需要删除的订单套餐关联ID
     * @return 结果
     */
    int deleteOrderPackageItemByIds(Long[] ids);
}