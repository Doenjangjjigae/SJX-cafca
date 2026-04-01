package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysTable;
import com.ruoyi.system.mapper.SysTableMapper;
import com.ruoyi.system.service.ISysTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysTableServiceImpl implements ISysTableService {

    @Autowired
    private SysTableMapper tableMapper;

    @Override
    public List<SysTable> selectTableList(SysTable table) {
        return tableMapper.selectTableList(table);
    }

    @Override
    public SysTable selectTableById(Long tableId) {
        return tableMapper.selectTableById(tableId);
    }

    @Override
    public int insertTable(SysTable table) {
        table.setCreateBy(SecurityUtils.getUsername());
        table.setUpdateBy(SecurityUtils.getUsername());
        table.setCreateTime(new java.util.Date());
        table.setUpdateTime(new java.util.Date());
        return tableMapper.insertTable(table);
    }

    @Override
    public int updateTable(SysTable table) {
        table.setUpdateBy(SecurityUtils.getUsername());
        table.setUpdateTime(new java.util.Date());
        return tableMapper.updateTable(table);
    }

    @Override
    public int deleteTableByIds(Long[] tableIds) {
        return tableMapper.deleteTableByIds(tableIds);
    }

    @Override
    public int updateTableStatus(Long tableId, String status) {
        SysTable table = new SysTable();
        table.setTableId(tableId);
        table.setStatus(status);
        table.setUpdateBy(SecurityUtils.getUsername());
        table.setUpdateTime(new java.util.Date());
        return tableMapper.updateTable(table);
    }

    @Override
    public boolean checkTableNameUnique(String tableName, Long tableId) {
        SysTable table = tableMapper.selectTableByTableName(tableName);
        if (table == null) {
            return true;
        }
        if (tableId != null && table.getTableId().equals(tableId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkTableNumberUnique(String tableNumber, Long tableId) {
        SysTable table = tableMapper.selectTableByTableNumber(tableNumber);
        if (table == null) {
            return true;
        }
        if (tableId != null && table.getTableId().equals(tableId)) {
            return true;
        }
        return false;
    }
}