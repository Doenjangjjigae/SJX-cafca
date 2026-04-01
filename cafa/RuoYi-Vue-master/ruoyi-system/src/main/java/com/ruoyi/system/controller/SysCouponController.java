package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysCoupon;
import com.ruoyi.system.service.ISysCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券Controller
 */
@RestController
@RequestMapping("/system/coupon")
public class SysCouponController extends BaseController {
    @Autowired
    private ISysCouponService sysCouponService;

    /**
     * 查询优惠券列表
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCoupon sysCoupon) {
        startPage();
        List<SysCoupon> list = sysCouponService.selectSysCouponList(sysCoupon);
        return getDataTable(list);
    }

    /**
     * 获取优惠券详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:query')")
    @GetMapping("/{couponId}")
    public AjaxResult getInfo(@PathVariable Long couponId) {
        return success(sysCouponService.selectSysCouponById(couponId));
    }

    /**
     * 新增优惠券
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SysCoupon sysCoupon) {
        return toAjax(sysCouponService.insertSysCoupon(sysCoupon));
    }

    /**
     * 修改优惠券
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SysCoupon sysCoupon) {
        return toAjax(sysCouponService.updateSysCoupon(sysCoupon));
    }

    /**
     * 删除优惠券
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:remove')")
    @DeleteMapping("/{couponId}")
    public AjaxResult remove(@PathVariable Long couponId) {
        return toAjax(sysCouponService.deleteSysCouponById(couponId));
    }

    /**
     * 批量删除优惠券
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:remove')")
    @DeleteMapping("/batch")
    public AjaxResult batchRemove(@RequestBody Long[] couponIds) {
        return toAjax(sysCouponService.deleteSysCouponByIds(couponIds));
    }

    /**
     * 批量发布优惠券
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:edit')")
    @PutMapping("/publish")
    public AjaxResult publish(@RequestBody java.util.Map<String, Object> params) {
        List<?> idList = (List<?>) params.get("couponIds");
        Long[] couponIds = idList.stream()
                .map(obj -> Long.valueOf(obj.toString()))
                .toArray(Long[]::new);
        String status = (String) params.get("status");
        return toAjax(sysCouponService.publishSysCoupon(couponIds, status));
    }

    /**
     * 发放优惠券给所有会员
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:edit')")
    @PostMapping("/issue/{couponId}")
    public AjaxResult issue(@PathVariable Long couponId) {
        int count = sysCouponService.issueCouponToAllMembers(couponId);
        return success("成功发放优惠券给" + count + "位会员");
    }

    /**
     * 批量发放优惠券给所有会员
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:edit')")
    @PostMapping("/issue")
    public AjaxResult batchIssue(@RequestBody Long[] couponIds) {
        int count = sysCouponService.issueCouponsToAllMembers(couponIds);
        return success("成功发放优惠券给" + count + "位会员");
    }

    /**
     * 查询已发布的优惠券列表（用于收银页面下拉框）
     */
    @GetMapping("/published")
    public AjaxResult getPublishedCoupons() {
        SysCoupon query = new SysCoupon();
        query.setStatus("0");
        List<SysCoupon> list = sysCouponService.selectSysCouponList(query);
        return success(list);
    }
}