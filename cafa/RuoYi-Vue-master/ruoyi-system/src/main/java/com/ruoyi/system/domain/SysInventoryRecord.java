package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存操作记录对象 sys_inventory_record
 * 
 * @author ruoyi
 * @date 2026-03-12
 */
public class SysInventoryRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 原料ID */
    @Excel(name = "原料ID")
    private Long materialId;

    /** 原料名称 */
    @Excel(name = "原料名称")
    private String materialName;

    /** 操作类型（1进货 2盘点） */
    @Excel(name = "操作类型", readConverterExp = "1=进货,2=盘点")
    private String operationType;

    /** 操作数量 */
    @Excel(name = "操作数量")
    private BigDecimal quantity;

    /** 操作前库存 */
    @Excel(name = "操作前库存")
    private BigDecimal beforeStock;

    /** 操作后库存 */
    @Excel(name = "操作后库存")
    private BigDecimal afterStock;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    /** 操作时间 */
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;

    public void setRecordId(Long recordId)
    {
        this.recordId = recordId;
    }

    public Long getRecordId()
    {
        return recordId;
    }
    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }

    public Long getMaterialId()
    {
        return materialId;
    }
    public void setMaterialName(String materialName)
    {
        this.materialName = materialName;
    }

    public String getMaterialName()
    {
        return materialName;
    }
    public void setOperationType(String operationType)
    {
        this.operationType = operationType;
    }

    public String getOperationType()
    {
        return operationType;
    }
    public void setQuantity(BigDecimal quantity)
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity()
    {
        return quantity;
    }
    public void setBeforeStock(BigDecimal beforeStock)
    {
        this.beforeStock = beforeStock;
    }

    public BigDecimal getBeforeStock()
    {
        return beforeStock;
    }
    public void setAfterStock(BigDecimal afterStock)
    {
        this.afterStock = afterStock;
    }

    public BigDecimal getAfterStock()
    {
        return afterStock;
    }
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getOperator()
    {
        return operator;
    }
    public void setOperationTime(Date operationTime)
    {
        this.operationTime = operationTime;
    }

    public Date getOperationTime()
    {
        return operationTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("materialId", getMaterialId())
            .append("materialName", getMaterialName())
            .append("operationType", getOperationType())
            .append("quantity", getQuantity())
            .append("beforeStock", getBeforeStock())
            .append("afterStock", getAfterStock())
            .append("operator", getOperator())
            .append("operationTime", getOperationTime())
            .append("remark", getRemark())
            .toString();
    }
}