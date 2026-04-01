package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysPackageProduct;

import java.util.List;

public interface SysPackageProductMapper {
    /**
     * 通过套餐ID查询套餐商品关联
     *
     * @param packageId 套餐ID
     * @return 套餐商品关联列表
     */
    List<SysPackageProduct> selectPackageProductByPackageId(Long packageId);

    /**
     * 新增套餐商品关联
     *
     * @param packageProduct 套餐商品关联信息
     * @return 结果
     */
    int insertPackageProduct(SysPackageProduct packageProduct);

    /**
     * 删除套餐商品关联
     *
     * @param packageId 套餐ID
     * @return 结果
     */
    int deletePackageProductByPackageId(Long packageId);
}