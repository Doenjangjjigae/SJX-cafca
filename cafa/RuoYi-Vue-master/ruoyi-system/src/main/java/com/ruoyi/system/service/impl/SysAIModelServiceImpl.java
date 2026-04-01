package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysAIModel;
import com.ruoyi.system.mapper.SysAIModelMapper;
import com.ruoyi.system.service.ISysAIModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysAIModelServiceImpl implements ISysAIModelService {

    @Autowired
    private SysAIModelMapper sysAIModelMapper;

    @Override
    public List<SysAIModel> selectAIModelList() {
        return sysAIModelMapper.selectAIModelList();
    }

    @Override
    public SysAIModel selectAIModelById(Long modelId) {
        return sysAIModelMapper.selectAIModelById(modelId);
    }

    @Override
    public SysAIModel selectEnabledModel() {
        return sysAIModelMapper.selectEnabledModel();
    }

    @Override
    public int insertAIModel(SysAIModel sysAIModel) {
        return sysAIModelMapper.insertAIModel(sysAIModel);
    }

    @Override
    public int updateAIModel(SysAIModel sysAIModel) {
        return sysAIModelMapper.updateAIModel(sysAIModel);
    }

    @Override
    public int deleteAIModelById(Long modelId) {
        return sysAIModelMapper.deleteAIModelById(modelId);
    }

    @Override
    @Transactional
    public int enableAIModel(Long modelId) {
        // 先将所有模型设置为禁用
        sysAIModelMapper.updateAllModelsDisabled();
        // 再将指定模型设置为启用
        SysAIModel model = new SysAIModel();
        model.setModelId(modelId);
        model.setIsEnabled(1);
        return sysAIModelMapper.updateAIModel(model);
    }
}