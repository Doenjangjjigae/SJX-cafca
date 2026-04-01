package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysMemberConsume;
import java.util.List;

/**
 * 会员消费记录Mapper接口
 */
public interface SysMemberConsumeMapper {
    /**
     * 查询会员消费记录
     * 
     * @param id 消费记录ID
     * @return 会员消费记录
     */
    public SysMemberConsume selectSysMemberConsumeById(Long id);

    /**
     * 根据会员ID查询消费记录列表
     * 
     * @param memberId 会员ID
     * @return 消费记录集合
     */
    public List<SysMemberConsume> selectSysMemberConsumeByMemberId(Long memberId);

    /**
     * 查询会员消费记录列表
     * 
     * @param sysMemberConsume 会员消费记录
     * @return 会员消费记录集合
     */
    public List<SysMemberConsume> selectSysMemberConsumeList(SysMemberConsume sysMemberConsume);

    /**
     * 新增会员消费记录
     * 
     * @param sysMemberConsume 会员消费记录
     * @return 结果
     */
    public int insertSysMemberConsume(SysMemberConsume sysMemberConsume);

    /**
     * 修改会员消费记录
     * 
     * @param sysMemberConsume 会员消费记录
     * @return 结果
     */
    public int updateSysMemberConsume(SysMemberConsume sysMemberConsume);

    /**
     * 删除会员消费记录
     * 
     * @param id 消费记录ID
     * @return 结果
     */
    public int deleteSysMemberConsumeById(Long id);

    /**
     * 批量删除会员消费记录
     * 
     * @param ids 需要删除的消费记录ID
     * @return 结果
     */
    public int deleteSysMemberConsumeByIds(Long[] ids);
}