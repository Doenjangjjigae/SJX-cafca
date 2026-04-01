package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysProductDefaultMaterial;
import java.util.List;

public interface ISysProductDefaultMaterialService {
    
    List<SysProductDefaultMaterial> selectDefaultMaterialsByProductId(Long productId);

    void saveDefaultMaterials(Long productId, List<SysProductDefaultMaterial> materials);

    void deleteDefaultMaterials(Long productId);
}
