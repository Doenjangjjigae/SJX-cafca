package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysMember;
import com.ruoyi.system.service.ISysMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员管理Controller
 */
@RestController
@RequestMapping("/system/member")
public class SysMemberController extends BaseController {
    @Autowired
    private ISysMemberService sysMemberService;

    /**
     * 查询会员列表
     */
    @PreAuthorize("@ss.hasPermi('system:member:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysMember sysMember) {
        startPage();
        List<SysMember> list = sysMemberService.selectSysMemberList(sysMember);
        return getDataTable(list);
    }

    /**
     * 根据会员ID查询会员信息
     */
    @PreAuthorize("@ss.hasPermi('system:member:query')")
    @GetMapping(value = "/{memberId}")
    public AjaxResult getInfo(@PathVariable("memberId") Long memberId) {
        return success(sysMemberService.selectMemberById(memberId));
    }

    /**
     * 新增会员
     */
    @PreAuthorize("@ss.hasPermi('system:member:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SysMember sysMember) {
        return toAjax(sysMemberService.insertSysMember(sysMember));
    }

    /**
     * 修改会员
     */
    @PreAuthorize("@ss.hasPermi('system:member:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SysMember sysMember) {
        return toAjax(sysMemberService.updateSysMember(sysMember));
    }

    /**
     * 删除会员
     */
    @PreAuthorize("@ss.hasPermi('system:member:remove')")
    @DeleteMapping("/{memberIds}")
    public AjaxResult remove(@PathVariable Long[] memberIds) {
        return toAjax(sysMemberService.deleteSysMemberByIds(memberIds));
    }
}
