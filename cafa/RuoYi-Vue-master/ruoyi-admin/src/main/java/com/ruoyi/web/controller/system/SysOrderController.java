package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysOrder;
import com.ruoyi.system.domain.SysOrderItem;
import com.ruoyi.system.domain.SysOrderCoupon;
import com.ruoyi.system.domain.SysOrderPackageItem;
import com.ruoyi.system.service.ISysOrderService;
import com.ruoyi.system.service.ISysOrderItemService;
import com.ruoyi.system.service.ISysOrderCouponService;
import com.ruoyi.system.service.ISysOrderPackageItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/order")
public class SysOrderController extends BaseController {
    @Autowired
    private ISysOrderService orderService;

    @Autowired
    private ISysOrderItemService orderItemService;

    @Autowired
    private ISysOrderCouponService orderCouponService;

    @Autowired
    private ISysOrderPackageItemService orderPackageItemService;

    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysOrder order) {
        startPage();
        List<SysOrder> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping("/info/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId) {
        return success(orderService.selectOrderById(orderId));
    }

    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping("/detail/{orderId}")
    public AjaxResult getDetail(@PathVariable("orderId") Long orderId) {
        Map<String, Object> result = new HashMap<>();
        
        SysOrder order = orderService.selectOrderById(orderId);
        result.put("order", order);
        
        List<SysOrderItem> items = orderItemService.selectOrderItemByOrderId(orderId);
        result.put("items", items);
        
        List<SysOrderPackageItem> packageItems = orderPackageItemService.selectOrderPackageItemByOrderId(orderId);
        result.put("packageItems", packageItems);
        
        List<SysOrderCoupon> coupons = orderCouponService.selectOrderCouponByOrderId(orderId);
        result.put("coupons", coupons);
        
        return success(result);
    }

    @PreAuthorize("@ss.hasPermi('system:order:add')")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SysOrder order) {
        return toAjax(orderService.insertOrder(order));
    }

    @PreAuthorize("@ss.hasPermi('system:order:edit')")
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody SysOrder order) {
        return toAjax(orderService.updateOrder(order));
    }

    @PreAuthorize("@ss.hasPermi('system:order:remove')")
    @DeleteMapping("/remove/{orderId}")
    public AjaxResult remove(@PathVariable("orderId") Long orderId) {
        return toAjax(orderService.deleteOrderById(orderId));
    }

    @PreAuthorize("@ss.hasPermi('system:order:remove')")
    @DeleteMapping("/remove")
    public AjaxResult remove(@RequestBody Long[] orderIds) {
        return toAjax(orderService.deleteOrderByIds(orderIds));
    }

    @PreAuthorize("@ss.hasPermi('system:order:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOrder order) {
        List<SysOrder> list = orderService.selectOrderList(order);
        ExcelUtil<SysOrder> util = new ExcelUtil<SysOrder>(SysOrder.class);
        util.exportExcel(response, list, "订单数据");
    }
}