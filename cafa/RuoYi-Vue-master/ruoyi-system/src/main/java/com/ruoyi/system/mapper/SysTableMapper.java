package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysTable;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SysTableMapper {
    List<SysTable> selectTableList(SysTable table);
    SysTable selectTableById(Long tableId);
    int insertTable(SysTable table);
    int updateTable(SysTable table);
    int deleteTableByIds(Long[] tableIds);
    SysTable selectTableByTableName(String tableName);
    SysTable selectTableByTableNumber(String tableNumber);
}