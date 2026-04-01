package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysMemberPoints;
import java.util.List;

/**
 * 会员积分Mapper接口
 */
public interface SysMemberPointsMapper {
    /**
     * 查询会员积分记录
     * 
     * @param id 积分记录ID
     * @return 会员积分记录
     */
    public SysMemberPoints selectSysMemberPointsById(Long id);

    /**
     * 根据会员ID查询积分记录列表
     * 
     * @param memberId 会员ID
     * @return 积分记录集合
     */
    public List<SysMemberPoints> selectSysMemberPointsByMemberId(Long memberId);

    /**
     * 查询会员积分记录列表
     * 
     * @param sysMemberPoints 会员积分记录
     * @return 会员积分记录集合
     */
    public List<SysMemberPoints> selectSysMemberPointsList(SysMemberPoints sysMemberPoints);

    /**
     * 新增会员积分记录
     * 
     * @param sysMemberPoints 会员积分记录
     * @return 结果
     */
    public int insertSysMemberPoints(SysMemberPoints sysMemberPoints);

    /**
     * 删除会员积分记录
     * 
     * @param id 积分记录ID
     * @return 结果
     */
    public int deleteSysMemberPointsById(Long id);

    /**
     * 批量删除会员积分记录
     * 
     * @param ids 需要删除的积分记录ID
     * @return 结果
     */
    public int deleteSysMemberPointsByIds(Long[] ids);
}