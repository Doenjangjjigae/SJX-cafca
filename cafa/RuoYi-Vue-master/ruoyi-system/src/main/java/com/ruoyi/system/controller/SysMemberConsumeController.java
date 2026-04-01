package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysMemberConsume;
import com.ruoyi.system.service.ISysMemberConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员消费记录Controller
 */
@RestController
@RequestMapping("/system/member/consume")
public class SysMemberConsumeController extends BaseController {
    @Autowired
    private ISysMemberConsumeService sysMemberConsumeService;

    /**
     * 查询会员消费记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:member:consume:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysMemberConsume sysMemberConsume) {
        startPage();
        List<SysMemberConsume> list = sysMemberConsumeService.selectSysMemberConsumeList(sysMemberConsume);
        return getDataTable(list);
    }

    /**
     * 根据会员ID查询消费记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:member:consume:query')")
    @GetMapping(value = "/member/{memberId}")
    public TableDataInfo getConsumeByMemberId(@PathVariable("memberId") Long memberId) {
        startPage();
        List<SysMemberConsume> list = sysMemberConsumeService.selectSysMemberConsumeByMemberId(memberId);
        return getDataTable(list);
    }

    /**
     * 新增会员消费记录
     */
    @PreAuthorize("@ss.hasPermi('system:member:consume:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SysMemberConsume sysMemberConsume) {
        return toAjax(sysMemberConsumeService.insertSysMemberConsume(sysMemberConsume));
    }

    /**
     * 修改会员消费记录
     */
    @PreAuthorize("@ss.hasPermi('system:member:consume:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SysMemberConsume sysMemberConsume) {
        return toAjax(sysMemberConsumeService.updateSysMemberConsume(sysMemberConsume));
    }

    /**
     * 删除会员消费记录
     */
    @PreAuthorize("@ss.hasPermi('system:member:consume:remove')")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysMemberConsumeService.deleteSysMemberConsumeByIds(ids));
    }
}