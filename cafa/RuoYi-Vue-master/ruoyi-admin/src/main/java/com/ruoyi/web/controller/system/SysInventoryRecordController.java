package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysInventoryRecord;
import com.ruoyi.system.service.ISysInventoryRecordService;
import com.ruoyi.system.service.ISysMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存操作记录Controller
 * 
 * @author ruoyi
 * @date 2026-03-12
 */
@RestController
@RequestMapping("/system/inventory/operation")
public class SysInventoryRecordController extends BaseController
{
    @Autowired
    private ISysInventoryRecordService sysInventoryRecordService;

    @Autowired
    private ISysMaterialService sysMaterialService;

    /**
     * 查询库存操作记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:operation:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysInventoryRecord sysInventoryRecord)
    {
        startPage();
        List<SysInventoryRecord> list = sysInventoryRecordService.selectSysInventoryRecordList(sysInventoryRecord);
        return getDataTable(list);
    }

    /**
     * 导出库存操作记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:operation:export')")
    @Log(title = "库存操作记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysInventoryRecord sysInventoryRecord)
    {
        List<SysInventoryRecord> list = sysInventoryRecordService.selectSysInventoryRecordList(sysInventoryRecord);
        ExcelUtil<SysInventoryRecord> util = new ExcelUtil<SysInventoryRecord>(SysInventoryRecord.class);
        return util.exportExcel(list, "库存操作记录数据");
    }

    /**
     * 获取库存操作记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:operation:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(sysInventoryRecordService.selectSysInventoryRecordById(recordId));
    }

    /**
     * 原料进货
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:operation:add')")
    @Log(title = "库存操作", businessType = BusinessType.INSERT)
    @PostMapping("/addStock")
    public AjaxResult addStock(@RequestParam Long materialId, @RequestParam BigDecimal quantity, @RequestParam String remark)
    {
        String operator = getUsername();
        return toAjax(sysMaterialService.addStock(materialId, quantity, operator, remark));
    }

    /**
     * 库存盘点
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:operation:stocktake')")
    @Log(title = "库存操作", businessType = BusinessType.UPDATE)
    @PostMapping("/stockTake")
    public AjaxResult stockTake(@RequestParam Long materialId, @RequestParam BigDecimal quantity, @RequestParam String remark)
    {
        String operator = getUsername();
        return toAjax(sysMaterialService.stockTake(materialId, quantity, operator, remark));
    }
}