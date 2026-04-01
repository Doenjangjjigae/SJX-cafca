package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 积分配置对象 sys_point_config
 */
public class SysPointConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 配置ID */
    private Long configId;

    /** 积分兑换比例（1元=多少积分） */
    private Integer pointRate;

    /** 状态（0-正常，1-禁用） */
    private String status;

    /** 备注 */
    private String remark;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Integer getPointRate() {
        return pointRate;
    }

    public void setPointRate(Integer pointRate) {
        this.pointRate = pointRate;
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