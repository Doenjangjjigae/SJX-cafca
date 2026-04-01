package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysInventoryRecord;
import com.ruoyi.system.mapper.SysInventoryRecordMapper;
import com.ruoyi.system.service.ISysInventoryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存操作记录Service实现类
 * 
 * @author ruoyi
 * @date 2026-03-12
 */
@Service
public class SysInventoryRecordServiceImpl implements ISysInventoryRecordService
{
    @Autowired
    private SysInventoryRecordMapper sysInventoryRecordMapper;

    /**
     * 查询库存操作记录
     * 
     * @param recordId 库存操作记录ID
     * @return 库存操作记录
     */
    @Override
    public SysInventoryRecord selectSysInventoryRecordById(Long recordId)
    {
        return sysInventoryRecordMapper.selectSysInventoryRecordById(recordId);
    }

    /**
     * 查询库存操作记录列表
     * 
     * @param sysInventoryRecord 库存操作记录
     * @return 库存操作记录集合
     */
    @Override
    public List<SysInventoryRecord> selectSysInventoryRecordList(SysInventoryRecord sysInventoryRecord)
    {
        return sysInventoryRecordMapper.selectSysInventoryRecordList(sysInventoryRecord);
    }

    /**
     * 新增库存操作记录
     * 
     * @param sysInventoryRecord 库存操作记录
     * @return 结果
     */
    @Override
    public int insertSysInventoryRecord(SysInventoryRecord sysInventoryRecord)
    {
        return sysInventoryRecordMapper.insertSysInventoryRecord(sysInventoryRecord);
    }

    /**
     * 修改库存操作记录
     * 
     * @param sysInventoryRecord 库存操作记录
     * @return 结果
     */
    @Override
    public int updateSysInventoryRecord(SysInventoryRecord sysInventoryRecord)
    {
        return sysInventoryRecordMapper.updateSysInventoryRecord(sysInventoryRecord);
    }

    /**
     * 删除库存操作记录
     * 
     * @param recordId 库存操作记录ID
     * @return 结果
     */
    @Override
    public int deleteSysInventoryRecordById(Long recordId)
    {
        return sysInventoryRecordMapper.deleteSysInventoryRecordById(recordId);
    }

    /**
     * 批量删除库存操作记录
     * 
     * @param recordIds 需要删除的库存操作记录ID
     * @return 结果
     */
    @Override
    public int deleteSysInventoryRecordByIds(Long[] recordIds)
    {
        return sysInventoryRecordMapper.deleteSysInventoryRecordByIds(recordIds);
    }
}