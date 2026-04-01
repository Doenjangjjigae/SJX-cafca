package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class SysOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNumber;

    /** 桌台ID */
    private Long tableId;

    /** 桌台名称 */
    @Excel(name = "桌台名称")
    private String tableName;

    /** 顾客姓名 */
    @Excel(name = "顾客姓名")
    private String customerName;

    /** 顾客电话 */
    @Excel(name = "顾客电话")
    private String customerPhone;

    /** 会员ID */
    private Long memberId;

    /** 总金额 */
    @Excel(name = "总金额", cellType = Excel.ColumnType.NUMERIC, scale = 2)
    private BigDecimal totalAmount;

    /** 优惠金额 */
    @Excel(name = "优惠金额", cellType = Excel.ColumnType.NUMERIC, scale = 2)
    private BigDecimal discountAmount;

    /** 实际支付金额 */
    @Excel(name = "实际支付金额", cellType = Excel.ColumnType.NUMERIC, scale = 2)
    private BigDecimal actualAmount;

    /** 状态（0-待支付，1-已支付，2-已完成，3-已取消） */
    @Excel(name = "状态", readConverterExp = "0=待支付,1=已支付,2=已完成,3=已取消")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 开始时间 */
    private Date beginTime;

    /** 结束时间 */
    private Date endTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}