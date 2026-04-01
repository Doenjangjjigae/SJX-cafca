package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class SysTable extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    private Long tableId;
    private String tableName;
    private String tableNumber;
    private Integer capacity;
    private String status;
    private String remark;
    
    public Long getTableId() {
        return tableId;
    }
    
    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getTableNumber() {
        return tableNumber;
    }
    
    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}