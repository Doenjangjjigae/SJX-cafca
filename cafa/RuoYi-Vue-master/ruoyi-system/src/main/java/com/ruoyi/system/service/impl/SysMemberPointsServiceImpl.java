package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysMember;
import com.ruoyi.system.domain.SysMemberPoints;
import com.ruoyi.system.mapper.SysMemberMapper;
import com.ruoyi.system.mapper.SysMemberPointsMapper;
import com.ruoyi.system.service.ISysMemberPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 会员积分Service实现类
 */
@Service
public class SysMemberPointsServiceImpl implements ISysMemberPointsService {
    @Autowired
    private SysMemberPointsMapper sysMemberPointsMapper;

    @Autowired
    private SysMemberMapper sysMemberMapper;

    @Override
    public SysMemberPoints selectSysMemberPointsById(Long id) {
        return sysMemberPointsMapper.selectSysMemberPointsById(id);
    }

    @Override
    public List<SysMemberPoints> selectSysMemberPointsByMemberId(Long memberId) {
        return sysMemberPointsMapper.selectSysMemberPointsByMemberId(memberId);
    }

    @Override
    public List<SysMemberPoints> selectSysMemberPointsList(SysMemberPoints sysMemberPoints) {
        return sysMemberPointsMapper.selectSysMemberPointsList(sysMemberPoints);
    }

    @Override
    public int insertSysMemberPoints(SysMemberPoints sysMemberPoints) {
        sysMemberPoints.setCreateBy(SecurityUtils.getUsername());
        sysMemberPoints.setCreateTime(new Date());
        return sysMemberPointsMapper.insertSysMemberPoints(sysMemberPoints);
    }

    @Override
    @Transactional
    public int insertManualPoints(Long memberId, Integer points, String remark) {
        // 查询会员信息
        SysMember sysMember = sysMemberMapper.selectSysMemberById(memberId);
        if (sysMember == null) {
            return 0;
        }

        // 创建积分记录
        SysMemberPoints sysMemberPoints = new SysMemberPoints();
        sysMemberPoints.setMemberId(memberId);
        sysMemberPoints.setMemberCode(sysMember.getMemberCode());
        sysMemberPoints.setPoints(points);
        sysMemberPoints.setType("1"); // 1-手动录入
        sysMemberPoints.setRemark(remark);
        sysMemberPoints.setCreateBy(SecurityUtils.getUsername());
        sysMemberPoints.setCreateTime(new Date());

        // 插入积分记录
        int result = sysMemberPointsMapper.insertSysMemberPoints(sysMemberPoints);
        if (result > 0) {
            // 更新会员总积分
            SysMember updateMember = new SysMember();
            updateMember.setMemberId(memberId);
            updateMember.setTotalPoints(points);
            updateMember.setUpdateBy(SecurityUtils.getUsername());
            updateMember.setUpdateTime(new Date());
            sysMemberMapper.updateSysMemberPoints(updateMember);
        }

        return result;
    }

    @Override
    public int deleteSysMemberPointsById(Long id) {
        return sysMemberPointsMapper.deleteSysMemberPointsById(id);
    }

    @Override
    public int deleteSysMemberPointsByIds(Long[] ids) {
        return sysMemberPointsMapper.deleteSysMemberPointsByIds(ids);
    }
}