package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysAIModel;

import java.util.List;

public interface SysAIModelMapper {
    List<SysAIModel> selectAIModelList();
    SysAIModel selectAIModelById(Long modelId);
    SysAIModel selectEnabledModel();
    int insertAIModel(SysAIModel sysAIModel);
    int updateAIModel(SysAIModel sysAIModel);
    int deleteAIModelById(Long modelId);
    int updateAllModelsDisabled();
}