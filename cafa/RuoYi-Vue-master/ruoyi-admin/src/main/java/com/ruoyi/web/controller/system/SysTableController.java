package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysTable;
import com.ruoyi.system.service.ISysTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/system/table")
public class SysTableController extends BaseController{

    @Autowired
    private ISysTableService tableService;

    @PreAuthorize("@ss.hasPermi('system:table:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTable table) {
        startPage();
        List<SysTable> list = tableService.selectTableList(table);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:table:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysTable table) {
        List<SysTable> list = tableService.selectTableList(table);
        ExcelUtil<SysTable> util = new ExcelUtil<SysTable>(SysTable.class);
        util.exportExcel(response, list, "桌台数据");
    }

    @PreAuthorize("@ss.hasPermi('system:table:query')")
    @GetMapping(value = "/{tableId}")
    public AjaxResult getInfo(@PathVariable Long tableId) {
        return AjaxResult.success(tableService.selectTableById(tableId));
    }

    @PreAuthorize("@ss.hasPermi('system:table:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SysTable table) {
        if (!tableService.checkTableNameUnique(table.getTableName(), null)) {
            return AjaxResult.error("桌台名称'" + table.getTableName() + "'已存在");
        }
        if (!tableService.checkTableNumberUnique(table.getTableNumber(), null)) {
            return AjaxResult.error("桌台编号'" + table.getTableNumber() + "'已存在");
        }
        return toAjax(tableService.insertTable(table));
    }

    @PreAuthorize("@ss.hasPermi('system:table:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SysTable table) {
        if (!tableService.checkTableNameUnique(table.getTableName(), table.getTableId())) {
            return AjaxResult.error("桌台名称'" + table.getTableName() + "'已存在");
        }
        if (!tableService.checkTableNumberUnique(table.getTableNumber(), table.getTableId())) {
            return AjaxResult.error("桌台编号'" + table.getTableNumber() + "'已存在");
        }
        return toAjax(tableService.updateTable(table));
    }

    @PreAuthorize("@ss.hasPermi('system:table:remove')")
    @DeleteMapping("/{tableIds}")
    public AjaxResult remove(@PathVariable Long[] tableIds) {
        return toAjax(tableService.deleteTableByIds(tableIds));
    }

    @PreAuthorize("@ss.hasPermi('system:table:edit')")
    @PutMapping("/status")
    public AjaxResult updateStatus(@RequestParam Long tableId, @RequestParam String status) {
        return toAjax(tableService.updateTableStatus(tableId, status));
    }
}