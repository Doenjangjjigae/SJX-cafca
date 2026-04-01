package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.service.ISysAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/system/analysis")
public class SysAnalysisController {

    @Autowired
    private ISysAnalysisService analysisService;

    /**
     * 销售额统计
     * @param type 统计类型：day, week, month
     * @param beginTime 开始时间
     * @param endTime 结束时间
     */
    @GetMapping("/sales")
    public R salesAnalysis(@RequestParam String type, 
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime, 
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        return R.ok(analysisService.getSalesAnalysis(type, beginTime, endTime));
    }

    /**
     * 订单数量分析
     * @param type 统计类型：day, week, month
     * @param beginTime 开始时间
     * @param endTime 结束时间
     */
    @GetMapping("/orders")
    public R orderAnalysis(@RequestParam String type, 
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime, 
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        return R.ok(analysisService.getOrderAnalysis(type, beginTime, endTime));
    }

    /**
     * 畅销商品排行榜
     * @param limit 限制数量
     * @param beginTime 开始时间
     * @param endTime 结束时间
     */
    @GetMapping("/topProducts")
    public R topProducts(@RequestParam(defaultValue = "10") int limit, 
                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime, 
                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        return R.ok(analysisService.getTopProducts(limit, beginTime, endTime));
    }
}
