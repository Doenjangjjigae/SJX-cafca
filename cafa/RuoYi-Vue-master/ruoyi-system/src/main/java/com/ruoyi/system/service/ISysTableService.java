package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysTable;
import java.util.List;

public interface ISysTableService {
    List<SysTable> selectTableList(SysTable table);
    SysTable selectTableById(Long tableId);
    int insertTable(SysTable table);
    int updateTable(SysTable table);
    int deleteTableByIds(Long[] tableIds);
    int updateTableStatus(Long tableId, String status);
    boolean checkTableNameUnique(String tableName, Long tableId);
    boolean checkTableNumberUnique(String tableNumber, Long tableId);
}