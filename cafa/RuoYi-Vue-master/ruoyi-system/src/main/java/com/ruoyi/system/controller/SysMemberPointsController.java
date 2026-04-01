package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysMemberPoints;
import com.ruoyi.system.service.ISysMemberPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员积分Controller
 */
@RestController
@RequestMapping("/system/member/points")
public class SysMemberPointsController extends BaseController {
    @Autowired
    private ISysMemberPointsService sysMemberPointsService;

    /**
     * 查询会员积分记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:member:points:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysMemberPoints sysMemberPoints) {
        startPage();
        List<SysMemberPoints> list = sysMemberPointsService.selectSysMemberPointsList(sysMemberPoints);
        return getDataTable(list);
    }

    /**
     * 根据会员ID查询积分记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:member:points:query')")
    @GetMapping(value = "/member/{memberId}")
    public TableDataInfo getPointsByMemberId(@PathVariable("memberId") Long memberId) {
        startPage();
        List<SysMemberPoints> list = sysMemberPointsService.selectSysMemberPointsByMemberId(memberId);
        return getDataTable(list);
    }

    /**
     * 手动录入会员积分
     */
    @PreAuthorize("@ss.hasPermi('system:member:points:add')")
    @PostMapping("/manual")
    public AjaxResult addManualPoints(@RequestParam Long memberId, @RequestParam Integer points, @RequestParam String remark) {
        return toAjax(sysMemberPointsService.insertManualPoints(memberId, points, remark));
    }

    /**
     * 删除会员积分记录
     */
    @PreAuthorize("@ss.hasPermi('system:member:points:remove')")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysMemberPointsService.deleteSysMemberPointsByIds(ids));
    }
}