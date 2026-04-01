package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysProductDefaultMaterial;
import com.ruoyi.system.mapper.SysProductDefaultMaterialMapper;
import com.ruoyi.system.service.ISysProductDefaultMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysProductDefaultMaterialServiceImpl implements ISysProductDefaultMaterialService {

    @Autowired
    private SysProductDefaultMaterialMapper defaultMaterialMapper;

    @Override
    public List<SysProductDefaultMaterial> selectDefaultMaterialsByProductId(Long productId) {
        return defaultMaterialMapper.selectDefaultMaterialsByProductId(productId);
    }

    @Override
    @Transactional
    public void saveDefaultMaterials(Long productId, List<SysProductDefaultMaterial> materials) {
        defaultMaterialMapper.deleteDefaultMaterialsByProductId(productId);

        if (materials == null || materials.isEmpty()) {
            return;
        }

        for (SysProductDefaultMaterial material : materials) {
            material.setProductId(productId);
            defaultMaterialMapper.insertDefaultMaterial(material);
        }
    }

    @Override
    @Transactional
    public void deleteDefaultMaterials(Long productId) {
        defaultMaterialMapper.deleteDefaultMaterialsByProductId(productId);
    }
}
