package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysOrderPackageItem;

import java.util.List;

public interface ISysOrderPackageItemService {
    /**
     * 查询订单套餐列表
     *
     * @param orderPackageItem 订单套餐信息
     * @return 订单套餐集合
     */
    public List<SysOrderPackageItem> selectOrderPackageItemList(SysOrderPackageItem orderPackageItem);

    /**
     * 通过ID查询订单套餐
     *
     * @param id ID
     * @return 订单套餐信息
     */
    public SysOrderPackageItem selectOrderPackageItemById(Long id);

    /**
     * 通过订单ID查询订单套餐
     *
     * @param orderId 订单ID
     * @return 订单套餐集合
     */
    public List<SysOrderPackageItem> selectOrderPackageItemByOrderId(Long orderId);

    /**
     * 新增订单套餐
     *
     * @param orderPackageItem 订单套餐信息
     * @return 结果
     */
    public int insertOrderPackageItem(SysOrderPackageItem orderPackageItem);

    /**
     * 修改订单套餐
     *
     * @param orderPackageItem 订单套餐信息
     * @return 结果
     */
    public int updateOrderPackageItem(SysOrderPackageItem orderPackageItem);

    /**
     * 删除订单套餐
     *
     * @param id ID
     * @return 结果
     */
    public int deleteOrderPackageItemById(Long id);

    /**
     * 批量删除订单套餐
     *
     * @param ids 需要删除的ID
     * @return 结果
     */
    public int deleteOrderPackageItemByIds(Long[] ids);
}
