package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysMaterial;

import java.util.List;

/**
 * 原料信息Mapper接口
 * 
 * @author ruoyi
 * @date 2026-03-12
 */
public interface SysMaterialMapper 
{
    /**
     * 查询原料信息
     * 
     * @param materialId 原料信息ID
     * @return 原料信息
     */
    public SysMaterial selectSysMaterialById(Long materialId);

    /**
     * 查询原料信息列表
     * 
     * @param sysMaterial 原料信息
     * @return 原料信息集合
     */
    public List<SysMaterial> selectSysMaterialList(SysMaterial sysMaterial);

    /**
     * 新增原料信息
     * 
     * @param sysMaterial 原料信息
     * @return 结果
     */
    public int insertSysMaterial(SysMaterial sysMaterial);

    /**
     * 修改原料信息
     * 
     * @param sysMaterial 原料信息
     * @return 结果
     */
    public int updateSysMaterial(SysMaterial sysMaterial);

    /**
     * 删除原料信息
     * 
     * @param materialId 原料信息ID
     * @return 结果
     */
    public int deleteSysMaterialById(Long materialId);

    /**
     * 批量删除原料信息
     * 
     * @param materialIds 需要删除的原料信息ID
     * @return 结果
     */
    public int deleteSysMaterialByIds(Long[] materialIds);

    /**
     * 查询低库存原料
     * 
     * @return 原料信息集合
     */
    public List<SysMaterial> selectLowStockMaterials();

    /**
     * 更新原料库存
     * 
     * @param sysMaterial 原料信息
     * @return 结果
     */
    public int updateMaterialStock(SysMaterial sysMaterial);
}