package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysMaterial;
import com.ruoyi.system.domain.SysProduct;
import com.ruoyi.system.domain.SysProductMaterial;
import com.ruoyi.system.mapper.SysMaterialMapper;
import com.ruoyi.system.mapper.SysProductMapper;
import com.ruoyi.system.service.ISysProductMaterialService;
import com.ruoyi.system.service.ISysProductService;
import com.ruoyi.system.service.ISysProductConfigService;
import com.ruoyi.system.service.ISysProductDefaultMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SysProductServiceImpl implements ISysProductService {
    @Autowired
    private SysProductMapper productMapper;

    @Autowired
    private SysMaterialMapper materialMapper;

    @Autowired
    private ISysProductMaterialService productMaterialService;

    @Autowired
    private ISysProductConfigService productConfigService;

    @Autowired
    private ISysProductDefaultMaterialService defaultMaterialService;

    @Override
    public List<SysProduct> selectProductList(SysProduct product) {
        List<SysProduct> products = productMapper.selectProductList(product);
        for (SysProduct p : products) {
            List<SysProductMaterial> materials = productMaterialService.selectProductMaterialByProductId(p.getProductId());
            p.setMaterials(materials);
            p.setConfigGroups(productConfigService.selectConfigGroupsByProductId(p.getProductId()));
        }
        return products;
    }

    @Override
    public SysProduct selectProductById(Long productId) {
        return productMapper.selectProductById(productId);
    }

    @Override
    public SysProduct selectProductWithMaterials(Long productId) {
        SysProduct product = productMapper.selectProductById(productId);
        if (product != null) {
            List<SysProductMaterial> materials = productMaterialService.selectProductMaterialByProductId(productId);
            product.setMaterials(materials);
            product.setConfigGroups(productConfigService.selectConfigGroupsByProductId(productId));
            product.setDefaultMaterials(defaultMaterialService.selectDefaultMaterialsByProductId(productId));
        }
        return product;
    }

    @Override
    @Transactional
    public int insertProduct(SysProduct product) {
        product.setCreateBy(SecurityUtils.getUsername());
        product.setCreateTime(DateUtils.getNowDate());
        product.setUpdateBy(SecurityUtils.getUsername());
        product.setUpdateTime(DateUtils.getNowDate());
        int result = productMapper.insertProduct(product);
        
        if (product.getMaterials() != null && !product.getMaterials().isEmpty()) {
            productMaterialService.saveProductMaterials(product.getProductId(), product.getMaterials());
        }
        
        if (product.getConfigGroups() != null && !product.getConfigGroups().isEmpty()) {
            productConfigService.saveProductConfig(product.getProductId(), product.getConfigGroups());
        }
        
        if (product.getDefaultMaterials() != null && !product.getDefaultMaterials().isEmpty()) {
            defaultMaterialService.saveDefaultMaterials(product.getProductId(), product.getDefaultMaterials());
        }
        
        SysMaterial material = new SysMaterial();
        material.setMaterialName(product.getProductName());
        material.setSpecification(product.getRemark());
        material.setUnit("份");
        material.setWarningThreshold(BigDecimal.ZERO);
        material.setCurrentStock(BigDecimal.ZERO);
        material.setStatus("0");
        material.setCreateBy(SecurityUtils.getUsername());
        material.setCreateTime(DateUtils.getNowDate());
        material.setUpdateBy(SecurityUtils.getUsername());
        material.setUpdateTime(DateUtils.getNowDate());
        material.setRemark("商品自动生成的物料信息");
        materialMapper.insertSysMaterial(material);
        
        return result;
    }

    @Override
    @Transactional
    public int updateProduct(SysProduct product) {
        product.setUpdateBy(SecurityUtils.getUsername());
        product.setUpdateTime(DateUtils.getNowDate());
        int result = productMapper.updateProduct(product);
        
        if (product.getMaterials() != null) {
            productMaterialService.saveProductMaterials(product.getProductId(), product.getMaterials());
        }
        
        if (product.getConfigGroups() != null) {
            productConfigService.saveProductConfig(product.getProductId(), product.getConfigGroups());
        }
        
        if (product.getDefaultMaterials() != null) {
            defaultMaterialService.saveDefaultMaterials(product.getProductId(), product.getDefaultMaterials());
        }
        
        return result;
    }

    @Override
    @Transactional
    public int deleteProductById(Long productId) {
        productMaterialService.saveProductMaterials(productId, null);
        productConfigService.deleteProductConfig(productId);
        defaultMaterialService.deleteDefaultMaterials(productId);
        return productMapper.deleteProductById(productId);
    }

    @Override
    @Transactional
    public int deleteProductByIds(Long[] productIds) {
        for (Long productId : productIds) {
            productMaterialService.saveProductMaterials(productId, null);
            productConfigService.deleteProductConfig(productId);
            defaultMaterialService.deleteDefaultMaterials(productId);
        }
        return productMapper.deleteProductByIds(productIds);
    }
}
