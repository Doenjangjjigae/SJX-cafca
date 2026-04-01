package com.ruoyi.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SysAnalysisMapper {

    /**
     * 获取销售额统计
     * @param type 统计类型：day, week, month
     * @param beginTime 开始时间
     * @param endTime 结束时间
     */
    List<Map<String, Object>> getSalesAnalysis(@Param("type") String type, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获取订单数量分析
     * @param type 统计类型：day, week, month
     * @param beginTime 开始时间
     * @param endTime 结束时间
     */
    List<Map<String, Object>> getOrderAnalysis(@Param("type") String type, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获取畅销商品排行榜
     * @param limit 限制数量
     * @param beginTime 开始时间
     * @param endTime 结束时间
     */
    List<Map<String, Object>> getTopProducts(@Param("limit") int limit, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
