package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysMember;
import java.util.List;

/**
 * 会员管理Mapper接口
 */
public interface SysMemberMapper {
    /**
     * 查询会员信息
     * 
     * @param memberId 会员ID
     * @return 会员信息
     */
    public SysMember selectSysMemberById(Long memberId);

    /**
     * 根据会员电话查询会员信息
     * 
     * @param phone 会员电话
     * @return 会员信息
     */
    public SysMember selectSysMemberByPhone(String phone);

    /**
     * 查询会员列表
     * 
     * @param sysMember 会员信息
     * @return 会员集合
     */
    public List<SysMember> selectSysMemberList(SysMember sysMember);

    /**
     * 新增会员
     * 
     * @param sysMember 会员信息
     * @return 结果
     */
    public int insertSysMember(SysMember sysMember);

    /**
     * 修改会员
     * 
     * @param sysMember 会员信息
     * @return 结果
     */
    public int updateSysMember(SysMember sysMember);

    /**
     * 修改会员积分
     * 
     * @param sysMember 会员信息
     * @return 结果
     */
    public int updateSysMemberPoints(SysMember sysMember);

    /**
     * 删除会员
     * 
     * @param memberId 会员ID
     * @return 结果
     */
    public int deleteSysMemberById(Long memberId);

    /**
     * 批量删除会员
     * 
     * @param memberIds 需要删除的会员ID
     * @return 结果
     */
    public int deleteSysMemberByIds(Long[] memberIds);
}