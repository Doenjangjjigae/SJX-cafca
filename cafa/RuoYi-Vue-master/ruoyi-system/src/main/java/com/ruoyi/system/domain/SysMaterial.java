package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 原料信息对象 sys_material
 * 
 * @author ruoyi
 * @date 2026-03-12
 */
public class SysMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 原料ID */
    private Long materialId;

    /** 原料名称 */
    @Excel(name = "原料名称")
    private String materialName;

    /** 原料规格 */
    @Excel(name = "原料规格")
    private String specification;

    /** 原料单位 */
    @Excel(name = "原料单位")
    private String unit;

    /** 库存预警阈值 */
    @Excel(name = "库存预警阈值")
    private BigDecimal warningThreshold;

    /** 当前库存 */
    @Excel(name = "当前库存")
    private BigDecimal currentStock;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

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
    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    public String getSpecification()
    {
        return specification;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void setWarningThreshold(BigDecimal warningThreshold)
    {
        this.warningThreshold = warningThreshold;
    }

    public BigDecimal getWarningThreshold()
    {
        return warningThreshold;
    }
    public void setCurrentStock(BigDecimal currentStock)
    {
        this.currentStock = currentStock;
    }

    public BigDecimal getCurrentStock()
    {
        return currentStock;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("materialId", getMaterialId())
            .append("materialName", getMaterialName())
            .append("specification", getSpecification())
            .append("unit", getUnit())
            .append("warningThreshold", getWarningThreshold())
            .append("currentStock", getCurrentStock())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}