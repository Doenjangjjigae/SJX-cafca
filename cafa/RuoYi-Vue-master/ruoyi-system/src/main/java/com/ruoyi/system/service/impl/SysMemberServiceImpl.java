package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysMember;
import com.ruoyi.system.mapper.SysMemberMapper;
import com.ruoyi.system.service.ISysMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 会员管理Service实现类
 */
@Service
public class SysMemberServiceImpl implements ISysMemberService {
    @Autowired
    private SysMemberMapper sysMemberMapper;

    @Override
    public SysMember selectMemberById(Long memberId) {
        return sysMemberMapper.selectSysMemberById(memberId);
    }

    @Override
    public SysMember selectMemberByPhone(String memberPhone) {
        return sysMemberMapper.selectSysMemberByPhone(memberPhone);
    }

    @Override
    public List<SysMember> selectMemberList(SysMember member) {
        return sysMemberMapper.selectSysMemberList(member);
    }

    @Override
    public int insertMember(SysMember member) {
        member.setCreateBy(SecurityUtils.getUsername());
        member.setCreateTime(new Date());
        member.setUpdateBy(SecurityUtils.getUsername());
        member.setUpdateTime(new Date());
        if (member.getTotalPoints() == null) {
            member.setTotalPoints(0);
        }
        return sysMemberMapper.insertSysMember(member);
    }

    @Override
    public int updateMember(SysMember member) {
        member.setUpdateBy(SecurityUtils.getUsername());
        member.setUpdateTime(new Date());
        return sysMemberMapper.updateSysMember(member);
    }

    @Override
    public int updateMemberPoints(Long memberId, Integer points) {
        SysMember sysMember = new SysMember();
        sysMember.setMemberId(memberId);
        sysMember.setTotalPoints(points);
        sysMember.setUpdateBy(SecurityUtils.getUsername());
        sysMember.setUpdateTime(new Date());
        return sysMemberMapper.updateSysMemberPoints(sysMember);
    }

    @Override
    public int deleteMemberById(Long memberId) {
        return sysMemberMapper.deleteSysMemberById(memberId);
    }

    @Override
    public int deleteMemberByIds(Long[] memberIds) {
        return sysMemberMapper.deleteSysMemberByIds(memberIds);
    }

    // @Override
    // public SysMember selectSysMemberById(Long memberId) {
    //     return sysMemberMapper.selectSysMemberById(memberId);
    // }

    @Override
    public List<SysMember> selectSysMemberList(SysMember sysMember) {
        return sysMemberMapper.selectSysMemberList(sysMember);
    }

    @Override
    public int insertSysMember(SysMember sysMember) {
        sysMember.setCreateBy(SecurityUtils.getUsername());
        sysMember.setCreateTime(new Date());
        sysMember.setUpdateBy(SecurityUtils.getUsername());
        sysMember.setUpdateTime(new Date());
        if (sysMember.getTotalPoints() == null) {
            sysMember.setTotalPoints(0);
        }
        return sysMemberMapper.insertSysMember(sysMember);
    }

    @Override
    public int updateSysMember(SysMember sysMember) {
        sysMember.setUpdateBy(SecurityUtils.getUsername());
        sysMember.setUpdateTime(new Date());
        return sysMemberMapper.updateSysMember(sysMember);
    }

    @Override
    public int deleteSysMemberById(Long memberId) {
        return sysMemberMapper.deleteSysMemberById(memberId);
    }

    @Override
    public int deleteSysMemberByIds(Long[] memberIds) {
        return sysMemberMapper.deleteSysMemberByIds(memberIds);
    }
}