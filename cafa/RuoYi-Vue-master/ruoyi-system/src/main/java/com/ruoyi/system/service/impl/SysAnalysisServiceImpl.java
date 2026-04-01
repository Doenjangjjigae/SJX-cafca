package com.ruoyi.system.service.impl;

import com.ruoyi.system.mapper.SysAnalysisMapper;
import com.ruoyi.system.service.ISysAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysAnalysisServiceImpl implements ISysAnalysisService {

    @Autowired
    private SysAnalysisMapper analysisMapper;

    @Override
    public List<Map<String, Object>> getSalesAnalysis(String type, Date beginTime, Date endTime) {
        return analysisMapper.getSalesAnalysis(type, beginTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getOrderAnalysis(String type, Date beginTime, Date endTime) {
        return analysisMapper.getOrderAnalysis(type, beginTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getTopProducts(int limit, Date beginTime, Date endTime) {
        return analysisMapper.getTopProducts(limit, beginTime, endTime);
    }
}
