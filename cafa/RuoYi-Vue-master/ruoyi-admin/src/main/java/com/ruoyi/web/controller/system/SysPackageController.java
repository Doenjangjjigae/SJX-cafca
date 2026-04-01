package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysPackage;
import com.ruoyi.system.service.ISysPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/package")
public class SysPackageController extends BaseController {
    @Autowired
    private ISysPackageService packageService;

    @PreAuthorize("@ss.hasPermi('system:package:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPackage pkg) {
        startPage();
        List<SysPackage> list = packageService.selectPackageList(pkg);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:package:query')")
    @GetMapping("/info/{packageId}")
    public AjaxResult getInfo(@PathVariable("packageId") Long packageId) {
        return success(packageService.selectPackageById(packageId));
    }

    @PreAuthorize("@ss.hasPermi('system:package:add')")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SysPackage pkg) {
        return toAjax(packageService.insertPackage(pkg));
    }

    @PreAuthorize("@ss.hasPermi('system:package:edit')")
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody SysPackage pkg) {
        return toAjax(packageService.updatePackage(pkg));
    }

    @PreAuthorize("@ss.hasPermi('system:package:remove')")
    @DeleteMapping("/remove/{packageId}")
    public AjaxResult remove(@PathVariable("packageId") Long packageId) {
        return toAjax(packageService.deletePackageById(packageId));
    }

    @PreAuthorize("@ss.hasPermi('system:package:remove')")
    @DeleteMapping("/remove")
    public AjaxResult remove(@RequestBody Long[] packageIds) {
        return toAjax(packageService.deletePackageByIds(packageIds));
    }
}