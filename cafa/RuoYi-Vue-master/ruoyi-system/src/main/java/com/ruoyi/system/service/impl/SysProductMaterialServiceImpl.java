package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysProductMaterial;
import com.ruoyi.system.domain.SysProductMaterialOption;
import com.ruoyi.system.domain.SysProductConfigMaterial;
import com.ruoyi.system.mapper.SysProductMaterialMapper;
import com.ruoyi.system.mapper.SysProductMaterialOptionMapper;
import com.ruoyi.system.mapper.SysProductConfigMaterialMapper;
import com.ruoyi.system.service.ISysProductMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysProductMaterialServiceImpl implements ISysProductMaterialService {

    @Autowired
    private SysProductMaterialMapper productMaterialMapper;

    @Autowired
    private SysProductMaterialOptionMapper optionMapper;

    @Autowired
    private SysProductConfigMaterialMapper configMaterialMapper;

    @Override
    public List<SysProductMaterial> selectProductMaterialByProductId(Long productId) {
        List<SysProductMaterial> materials = productMaterialMapper.selectProductMaterialByProductId(productId);
        for (SysProductMaterial material : materials) {
            if ("1".equals(material.getIsAdjustable())) {
                List<SysProductMaterialOption> options = optionMapper.selectOptionsByProductMaterialId(material.getId());
                material.setOptions(options);
            }
        }
        return materials;
    }

    @Override
    public SysProductMaterial selectProductMaterialById(Long id) {
        SysProductMaterial material = productMaterialMapper.selectProductMaterialById(id);
        if (material != null && "1".equals(material.getIsAdjustable())) {
            List<SysProductMaterialOption> options = optionMapper.selectOptionsByProductMaterialId(id);
            material.setOptions(options);
        }
        return material;
    }

    @Override
    public int insertProductMaterial(SysProductMaterial productMaterial) {
        productMaterial.setCreateTime(DateUtils.getNowDate());
        productMaterial.setUpdateTime(DateUtils.getNowDate());
        int result = productMaterialMapper.insertProductMaterial(productMaterial);
        
        if ("1".equals(productMaterial.getIsAdjustable()) && productMaterial.getOptions() != null) {
            for (SysProductMaterialOption option : productMaterial.getOptions()) {
                option.setProductMaterialId(productMaterial.getId());
            }
            if (!productMaterial.getOptions().isEmpty()) {
                optionMapper.batchInsertOptions(productMaterial.getOptions());
            }
        }
        return result;
    }

    @Override
    public int updateProductMaterial(SysProductMaterial productMaterial) {
        productMaterial.setUpdateTime(DateUtils.getNowDate());
        int result = productMaterialMapper.updateProductMaterial(productMaterial);
        
        if ("1".equals(productMaterial.getIsAdjustable()) && productMaterial.getOptions() != null) {
            optionMapper.deleteOptionsByProductMaterialId(productMaterial.getId());
            for (SysProductMaterialOption option : productMaterial.getOptions()) {
                option.setProductMaterialId(productMaterial.getId());
            }
            if (!productMaterial.getOptions().isEmpty()) {
                optionMapper.batchInsertOptions(productMaterial.getOptions());
            }
        }
        return result;
    }

    @Override
    public int deleteProductMaterialById(Long id) {
        optionMapper.deleteOptionsByProductMaterialId(id);
        return productMaterialMapper.deleteProductMaterialById(id);
    }

    @Override
    @Transactional
    public int saveProductMaterials(Long productId, List<SysProductMaterial> materials) {
        List<SysProductMaterial> existingMaterials = productMaterialMapper.selectProductMaterialByProductId(productId);
        List<Long> existingIds = new ArrayList<>();
        for (SysProductMaterial m : existingMaterials) {
            existingIds.add(m.getId());
        }
        if (!existingIds.isEmpty()) {
            optionMapper.deleteOptionsByProductMaterialIds(existingIds);
        }
        productMaterialMapper.deleteProductMaterialByProductId(productId);
        
        if (materials == null || materials.isEmpty()) {
            return 1;
        }
        
        for (SysProductMaterial material : materials) {
            material.setProductId(productId);
            material.setCreateTime(DateUtils.getNowDate());
            material.setUpdateTime(DateUtils.getNowDate());
        }
        productMaterialMapper.batchInsertProductMaterial(materials);
        
        List<SysProductMaterialOption> allOptions = new ArrayList<>();
        for (SysProductMaterial material : materials) {
            if ("1".equals(material.getIsAdjustable()) && material.getOptions() != null) {
                for (SysProductMaterialOption option : material.getOptions()) {
                    option.setProductMaterialId(material.getId());
                    allOptions.add(option);
                }
            }
        }
        if (!allOptions.isEmpty()) {
            optionMapper.batchInsertOptions(allOptions);
        }
        
        return 1;
    }

    @Override
    public List<SysProductConfigMaterial> selectMaterialsByConfigOptionId(Long configOptionId) {
        return configMaterialMapper.selectMaterialsByOptionId(configOptionId);
    }
}
