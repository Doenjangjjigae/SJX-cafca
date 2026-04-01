package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysProductDefaultMaterial;
import java.util.List;

public interface SysProductDefaultMaterialMapper {
    
    List<SysProductDefaultMaterial> selectDefaultMaterialsByProductId(Long productId);

    int insertDefaultMaterial(SysProductDefaultMaterial defaultMaterial);

    int updateDefaultMaterial(SysProductDefaultMaterial defaultMaterial);

    int deleteDefaultMaterialById(Long id);

    int deleteDefaultMaterialsByProductId(Long productId);
}
