package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysPointConfig;
import java.util.List;

/**
 * 积分配置Service接口
 */
public interface ISysPointConfigService {
    /**
     * 查询积分配置
     * 
     * @param configId 配置ID
     * @return 积分配置
     */
    public SysPointConfig selectSysPointConfigById(Long configId);

    /**
     * 查询积分配置列表
     * 
     * @param sysPointConfig 积分配置
     * @return 积分配置集合
     */
    public List<SysPointConfig> selectSysPointConfigList(SysPointConfig sysPointConfig);

    /**
     * 查询当前生效的积分配置
     * 
     * @return 积分配置
     */
    public SysPointConfig selectCurrentPointConfig();

    /**
     * 新增积分配置
     * 
     * @param sysPointConfig 积分配置
     * @return 结果
     */
    public int insertSysPointConfig(SysPointConfig sysPointConfig);

    /**
     * 修改积分配置
     * 
     * @param sysPointConfig 积分配置
     * @return 结果
     */
    public int updateSysPointConfig(SysPointConfig sysPointConfig);

    /**
     * 删除积分配置
     * 
     * @param configId 配置ID
     * @return 结果
     */
    public int deleteSysPointConfigById(Long configId);
}