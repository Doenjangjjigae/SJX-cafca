package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysPointConfig;
import com.ruoyi.system.service.ISysPointConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 积分配置Controller
 */
@RestController
@RequestMapping("/system/pointConfig")
public class SysPointConfigController extends BaseController {
    @Autowired
    private ISysPointConfigService sysPointConfigService;

    /**
     * 查询积分配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:pointConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPointConfig sysPointConfig) {
        startPage();
        List<SysPointConfig> list = sysPointConfigService.selectSysPointConfigList(sysPointConfig);
        return getDataTable(list);
    }

    /**
     * 获取当前积分配置
     */
    @GetMapping("/current")
    public AjaxResult getCurrentConfig() {
        SysPointConfig config = sysPointConfigService.selectCurrentPointConfig();
        return success(config);
    }

    /**
     * 获取积分配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:pointConfig:query')")
    @GetMapping("/{configId}")
    public AjaxResult getInfo(@PathVariable Long configId) {
        return success(sysPointConfigService.selectSysPointConfigById(configId));
    }

    /**
     * 新增积分配置
     */
    @PreAuthorize("@ss.hasPermi('system:pointConfig:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SysPointConfig sysPointConfig) {
        return toAjax(sysPointConfigService.insertSysPointConfig(sysPointConfig));
    }

    /**
     * 修改积分配置
     */
    @PreAuthorize("@ss.hasPermi('system:pointConfig:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SysPointConfig sysPointConfig) {
        return toAjax(sysPointConfigService.updateSysPointConfig(sysPointConfig));
    }

    /**
     * 删除积分配置
     */
    @PreAuthorize("@ss.hasPermi('system:pointConfig:remove')")
    @DeleteMapping("/{configId}")
    public AjaxResult remove(@PathVariable Long configId) {
        return toAjax(sysPointConfigService.deleteSysPointConfigById(configId));
    }
}