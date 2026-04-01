package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysProductMaterial;
import com.ruoyi.system.domain.SysProductConfigMaterial;
import java.util.List;

public interface ISysProductMaterialService {
    
    List<SysProductMaterial> selectProductMaterialByProductId(Long productId);

    SysProductMaterial selectProductMaterialById(Long id);

    int insertProductMaterial(SysProductMaterial productMaterial);

    int updateProductMaterial(SysProductMaterial productMaterial);

    int deleteProductMaterialById(Long id);

    int saveProductMaterials(Long productId, List<SysProductMaterial> materials);

    List<SysProductConfigMaterial> selectMaterialsByConfigOptionId(Long configOptionId);
}
