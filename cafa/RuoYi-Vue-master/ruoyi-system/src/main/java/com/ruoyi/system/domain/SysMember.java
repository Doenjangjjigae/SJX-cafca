package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.math.BigDecimal;

public class SysMember extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 会员ID */
    private Long memberId;

    /** 会员姓名 */
    private String memberName;

    /** 会员编号 */
    private String memberCode;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 出生日期 */
    private Date birthdate;

    /** 性别（0-男，1-女） */
    private String gender;

    /** 地址 */
    private String address;

    /** 总积分 */
    private Integer totalPoints;

    /** 状态（0-正常，1-禁用） */
    private String status;

    /** 备注 */
    private String remark;
    

    
    /** 备注 */
    private BigDecimal discountRate;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }


    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }




    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
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