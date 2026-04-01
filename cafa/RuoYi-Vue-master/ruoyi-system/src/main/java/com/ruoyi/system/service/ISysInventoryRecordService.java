package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysInventoryRecord;

import java.util.List;

/**
 * 库存操作记录Service接口
 * 
 * @author ruoyi
 * @date 2026-03-12
 */
public interface ISysInventoryRecordService 
{
    /**
     * 查询库存操作记录
     * 
     * @param recordId 库存操作记录ID
     * @return 库存操作记录
     */
    public SysInventoryRecord selectSysInventoryRecordById(Long recordId);

    /**
     * 查询库存操作记录列表
     * 
     * @param sysInventoryRecord 库存操作记录
     * @return 库存操作记录集合
     */
    public List<SysInventoryRecord> selectSysInventoryRecordList(SysInventoryRecord sysInventoryRecord);

    /**
     * 新增库存操作记录
     * 
     * @param sysInventoryRecord 库存操作记录
     * @return 结果
     */
    public int insertSysInventoryRecord(SysInventoryRecord sysInventoryRecord);

    /**
     * 修改库存操作记录
     * 
     * @param sysInventoryRecord 库存操作记录
     * @return 结果
     */
    public int updateSysInventoryRecord(SysInventoryRecord sysInventoryRecord);

    /**
     * 删除库存操作记录
     * 
     * @param recordId 库存操作记录ID
     * @return 结果
     */
    public int deleteSysInventoryRecordById(Long recordId);

    /**
     * 批量删除库存操作记录
     * 
     * @param recordIds 需要删除的库存操作记录ID
     * @return 结果
     */
    public int deleteSysInventoryRecordByIds(Long[] recordIds);
}