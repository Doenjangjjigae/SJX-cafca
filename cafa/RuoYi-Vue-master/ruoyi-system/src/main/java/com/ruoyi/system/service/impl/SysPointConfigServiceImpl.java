package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysPointConfig;
import com.ruoyi.system.mapper.SysPointConfigMapper;
import com.ruoyi.system.service.ISysPointConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 积分配置Service实现类
 */
@Service
public class SysPointConfigServiceImpl implements ISysPointConfigService {
    @Autowired
    private SysPointConfigMapper sysPointConfigMapper;

    @Override
    public SysPointConfig selectSysPointConfigById(Long configId) {
        return sysPointConfigMapper.selectSysPointConfigById(configId);
    }

    @Override
    public List<SysPointConfig> selectSysPointConfigList(SysPointConfig sysPointConfig) {
        return sysPointConfigMapper.selectSysPointConfigList(sysPointConfig);
    }

    @Override
    public SysPointConfig selectCurrentPointConfig() {
        return sysPointConfigMapper.selectCurrentPointConfig();
    }

    @Override
    public int insertSysPointConfig(SysPointConfig sysPointConfig) {
        sysPointConfig.setCreateBy(SecurityUtils.getUsername());
        sysPointConfig.setCreateTime(new Date());
        sysPointConfig.setUpdateBy(SecurityUtils.getUsername());
        sysPointConfig.setUpdateTime(new Date());
        return sysPointConfigMapper.insertSysPointConfig(sysPointConfig);
    }

    @Override
    public int updateSysPointConfig(SysPointConfig sysPointConfig) {
        sysPointConfig.setUpdateBy(SecurityUtils.getUsername());
        sysPointConfig.setUpdateTime(new Date());
        return sysPointConfigMapper.updateSysPointConfig(sysPointConfig);
    }

    @Override
    public int deleteSysPointConfigById(Long configId) {
        return sysPointConfigMapper.deleteSysPointConfigById(configId);
    }
}