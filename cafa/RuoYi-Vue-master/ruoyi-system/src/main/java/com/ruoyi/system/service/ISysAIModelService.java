package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysAIModel;

import java.util.List;

public interface ISysAIModelService {
    List<SysAIModel> selectAIModelList();
    SysAIModel selectAIModelById(Long modelId);
    SysAIModel selectEnabledModel();
    int insertAIModel(SysAIModel sysAIModel);
    int updateAIModel(SysAIModel sysAIModel);
    int deleteAIModelById(Long modelId);
    int enableAIModel(Long modelId);
}