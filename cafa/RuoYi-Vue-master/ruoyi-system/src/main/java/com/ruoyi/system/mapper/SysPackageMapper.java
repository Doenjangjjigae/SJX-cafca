package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysPackage;

import java.util.List;

public interface SysPackageMapper {
    /**
     * 查询套餐列表
     *
     * @param pkg 套餐信息
     * @return 套餐列表
     */
    List<SysPackage> selectPackageList(SysPackage pkg);

    /**
     * 通过套餐ID查询套餐
     *
     * @param packageId 套餐ID
     * @return 套餐信息
     */
    SysPackage selectPackageById(Long packageId);

    /**
     * 新增套餐
     *
     * @param pkg 套餐信息
     * @return 结果
     */
    int insertPackage(SysPackage pkg);

    /**
     * 修改套餐
     *
     * @param pkg 套餐信息
     * @return 结果
     */
    int updatePackage(SysPackage pkg);

    /**
     * 删除套餐
     *
     * @param packageId 套餐ID
     * @return 结果
     */
    int deletePackageById(Long packageId);

    /**
     * 批量删除套餐
     *
     * @param packageIds 需要删除的套餐ID
     * @return 结果
     */
    int deletePackageByIds(Long[] packageIds);
}