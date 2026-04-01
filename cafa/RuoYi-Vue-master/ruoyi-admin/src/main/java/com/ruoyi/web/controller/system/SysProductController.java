package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysProduct;
import com.ruoyi.system.service.ISysProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/product")
public class SysProductController extends BaseController {
    @Autowired
    private ISysProductService productService;

    @PreAuthorize("@ss.hasPermi('system:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProduct product) {
        startPage();
        List<SysProduct> list = productService.selectProductList(product);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:product:query')")
    @GetMapping("/info/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId) {
        return success(productService.selectProductWithMaterials(productId));
    }

    @PreAuthorize("@ss.hasPermi('system:product:add')")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SysProduct product) {
        return toAjax(productService.insertProduct(product));
    }

    @PreAuthorize("@ss.hasPermi('system:product:edit')")
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody SysProduct product) {
        return toAjax(productService.updateProduct(product));
    }

    @PreAuthorize("@ss.hasPermi('system:product:remove')")
    @DeleteMapping("/remove/{productId}")
    public AjaxResult remove(@PathVariable("productId") Long productId) {
        return toAjax(productService.deleteProductById(productId));
    }

    @PreAuthorize("@ss.hasPermi('system:product:remove')")
    @DeleteMapping("/remove")
    public AjaxResult remove(@RequestBody Long[] productIds) {
        return toAjax(productService.deleteProductByIds(productIds));
    }
}
