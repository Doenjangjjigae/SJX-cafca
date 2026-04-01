package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysProductConfigMaterial;
import java.util.List;

public interface SysProductConfigMaterialMapper {
    
    List<SysProductConfigMaterial> selectMaterialsByOptionId(Long configOptionId);

    int insertConfigMaterial(SysProductConfigMaterial configMaterial);

    int deleteMaterialsByOptionId(Long configOptionId);
}
