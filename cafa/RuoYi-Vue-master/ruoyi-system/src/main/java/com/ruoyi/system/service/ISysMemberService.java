package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysMember;
import java.util.List;

/**
 * 会员管理Service接口
 */
public interface ISysMemberService {
    /**
     * 查询会员信息
     * 
     * @param memberId 会员ID
     * @return 会员信息
     */
    public SysMember selectMemberById(Long memberId);

    /**
     * 根据会员电话查询会员信息
     * 
     * @param memberPhone 会员电话
     * @return 会员信息
     */
    public SysMember selectMemberByPhone(String memberPhone);

    /**
     * 查询会员列表
     * 
     * @param member 会员信息
     * @return 会员集合
     */
    public List<SysMember> selectMemberList(SysMember member);

    /**
     * 新增会员
     * 
     * @param member 会员信息
     * @return 结果
     */
    public int insertMember(SysMember member);

    /**
     * 修改会员
     * 
     * @param member 会员信息
     * @return 结果
     */
    public int updateMember(SysMember member);

    /**
     * 修改会员积分
     * 
     * @param memberId 会员ID
     * @param points 积分数量
     * @return 结果
     */
    public int updateMemberPoints(Long memberId, Integer points);

    /**
     * 删除会员
     * 
     * @param memberId 会员ID
     * @return 结果
     */
    public int deleteMemberById(Long memberId);

    /**
     * 批量删除会员
     * 
     * @param memberIds 需要删除的会员ID
     * @return 结果
     */
    public int deleteMemberByIds(Long[] memberIds);

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