package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysMemberConsume;
import com.ruoyi.system.mapper.SysMemberConsumeMapper;
import com.ruoyi.system.service.ISysMemberConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 会员消费记录Service实现类
 */
@Service
public class SysMemberConsumeServiceImpl implements ISysMemberConsumeService {
    @Autowired
    private SysMemberConsumeMapper sysMemberConsumeMapper;

    @Override
    public SysMemberConsume selectSysMemberConsumeById(Long id) {
        return sysMemberConsumeMapper.selectSysMemberConsumeById(id);
    }

    @Override
    public List<SysMemberConsume> selectSysMemberConsumeByMemberId(Long memberId) {
        return sysMemberConsumeMapper.selectSysMemberConsumeByMemberId(memberId);
    }

    @Override
    public List<SysMemberConsume> selectSysMemberConsumeList(SysMemberConsume sysMemberConsume) {
        return sysMemberConsumeMapper.selectSysMemberConsumeList(sysMemberConsume);
    }

    @Override
    public int insertSysMemberConsume(SysMemberConsume sysMemberConsume) {
        sysMemberConsume.setCreateBy(SecurityUtils.getUsername());
        sysMemberConsume.setCreateTime(new Date());
        sysMemberConsume.setUpdateBy(SecurityUtils.getUsername());
        sysMemberConsume.setUpdateTime(new Date());
        return sysMemberConsumeMapper.insertSysMemberConsume(sysMemberConsume);
    }

    @Override
    public int updateSysMemberConsume(SysMemberConsume sysMemberConsume) {
        sysMemberConsume.setUpdateBy(SecurityUtils.getUsername());
        sysMemberConsume.setUpdateTime(new Date());
        return sysMemberConsumeMapper.updateSysMemberConsume(sysMemberConsume);
    }

    @Override
    public int deleteSysMemberConsumeById(Long id) {
        return sysMemberConsumeMapper.deleteSysMemberConsumeById(id);
    }

    @Override
    public int deleteSysMemberConsumeByIds(Long[] ids) {
        return sysMemberConsumeMapper.deleteSysMemberConsumeByIds(ids);
    }
}