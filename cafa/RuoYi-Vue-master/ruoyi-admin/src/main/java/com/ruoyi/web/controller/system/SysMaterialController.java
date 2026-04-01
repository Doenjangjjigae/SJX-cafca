package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysMaterial;
import com.ruoyi.system.service.ISysMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 原料信息Controller
 * 
 * @author ruoyi
 * @date 2026-03-12
 */
@RestController
@RequestMapping("/system/inventory/material")
public class SysMaterialController extends BaseController
{
    @Autowired
    private ISysMaterialService sysMaterialService;

    /**
     * 查询原料信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:material:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysMaterial sysMaterial)
    {
        startPage();
        List<SysMaterial> list = sysMaterialService.selectSysMaterialList(sysMaterial);
        return getDataTable(list);
    }

    /**
     * 导出原料信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:material:export')")
    @Log(title = "原料信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysMaterial sysMaterial)
    {
        List<SysMaterial> list = sysMaterialService.selectSysMaterialList(sysMaterial);
        ExcelUtil<SysMaterial> util = new ExcelUtil<SysMaterial>(SysMaterial.class);
        return util.exportExcel(list, "原料信息数据");
    }

    /**
     * 获取原料信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:material:query')")
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") Long materialId)
    {
        return AjaxResult.success(sysMaterialService.selectSysMaterialById(materialId));
    }

    /**
     * 新增原料信息
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:material:add')")
    @Log(title = "原料信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysMaterial sysMaterial)
    {
        return toAjax(sysMaterialService.insertSysMaterial(sysMaterial));
    }

    /**
     * 修改原料信息
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:material:edit')")
    @Log(title = "原料信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysMaterial sysMaterial)
    {
        return toAjax(sysMaterialService.updateSysMaterial(sysMaterial));
    }

    /**
     * 删除原料信息
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:material:remove')")
    @Log(title = "原料信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable Long[] materialIds)
    {
        return toAjax(sysMaterialService.deleteSysMaterialByIds(materialIds));
    }

    /**
     * 设置库存预警阈值
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:warning:set')")
    @Log(title = "库存预警", businessType = BusinessType.UPDATE)
    @PutMapping("/setThreshold")
    public AjaxResult setThreshold(@RequestParam Long materialId, @RequestParam BigDecimal warningThreshold)
    {
        return toAjax(sysMaterialService.setWarningThreshold(materialId, warningThreshold));
    }
}