package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysMaterial;
import com.ruoyi.system.service.ISysMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 库存预警Controller
 * 
 * @author ruoyi
 * @date 2026-03-12
 */
@RestController
@RequestMapping("/system/inventory/warning")
public class SysInventoryWarningController extends BaseController
{
    @Autowired
    private ISysMaterialService sysMaterialService;

    /**
     * 查询低库存原料列表
     */
    @PreAuthorize("@ss.hasPermi('system:inventory:warning:list')")
    @GetMapping("/list")
    public TableDataInfo list()
    {
        startPage();
        List<SysMaterial> list = sysMaterialService.selectLowStockMaterials();
        return getDataTable(list);
    }
}