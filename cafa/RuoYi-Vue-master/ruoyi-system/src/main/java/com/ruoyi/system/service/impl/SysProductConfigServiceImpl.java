package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysProductConfigGroup;
import com.ruoyi.system.domain.SysProductConfigOption;
import com.ruoyi.system.domain.SysProductConfigMaterial;
import com.ruoyi.system.mapper.SysProductConfigGroupMapper;
import com.ruoyi.system.mapper.SysProductConfigOptionMapper;
import com.ruoyi.system.mapper.SysProductConfigMaterialMapper;
import com.ruoyi.system.service.ISysProductConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysProductConfigServiceImpl implements ISysProductConfigService {

    @Autowired
    private SysProductConfigGroupMapper configGroupMapper;

    @Autowired
    private SysProductConfigOptionMapper configOptionMapper;

    @Autowired
    private SysProductConfigMaterialMapper configMaterialMapper;

    @Override
    public List<SysProductConfigGroup> selectConfigGroupsByProductId(Long productId) {
        List<SysProductConfigGroup> groups = configGroupMapper.selectConfigGroupsByProductId(productId);
        for (SysProductConfigGroup group : groups) {
            List<SysProductConfigOption> options = configOptionMapper.selectConfigOptionsByGroupId(group.getConfigGroupId());
            for (SysProductConfigOption option : options) {
                List<SysProductConfigMaterial> materials = configMaterialMapper.selectMaterialsByOptionId(option.getConfigOptionId());
                option.setMaterials(materials);
            }
            group.setOptions(options);
        }
        return groups;
    }

    @Override
    @Transactional
    public void saveProductConfig(Long productId, List<SysProductConfigGroup> configGroups) {
        deleteProductConfig(productId);

        if (configGroups == null || configGroups.isEmpty()) {
            return;
        }

        for (SysProductConfigGroup group : configGroups) {
            group.setProductId(productId);
            configGroupMapper.insertConfigGroup(group);

            if (group.getOptions() != null) {
                for (SysProductConfigOption option : group.getOptions()) {
                    option.setConfigGroupId(group.getConfigGroupId());
                    configOptionMapper.insertConfigOption(option);

                    if (option.getMaterials() != null) {
                        for (SysProductConfigMaterial material : option.getMaterials()) {
                            material.setConfigOptionId(option.getConfigOptionId());
                            configMaterialMapper.insertConfigMaterial(material);
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void deleteProductConfig(Long productId) {
        List<SysProductConfigGroup> groups = configGroupMapper.selectConfigGroupsByProductId(productId);
        for (SysProductConfigGroup group : groups) {
            List<SysProductConfigOption> options = configOptionMapper.selectConfigOptionsByGroupId(group.getConfigGroupId());
            for (SysProductConfigOption option : options) {
                configMaterialMapper.deleteMaterialsByOptionId(option.getConfigOptionId());
            }
            configOptionMapper.deleteConfigOptionsByGroupId(group.getConfigGroupId());
        }
        configGroupMapper.deleteConfigGroupsByProductId(productId);
    }
}
