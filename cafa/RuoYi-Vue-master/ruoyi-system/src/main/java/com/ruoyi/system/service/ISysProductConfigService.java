package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysProductConfigGroup;
import java.util.List;

public interface ISysProductConfigService {
    
    List<SysProductConfigGroup> selectConfigGroupsByProductId(Long productId);

    void saveProductConfig(Long productId, List<SysProductConfigGroup> configGroups);

    void deleteProductConfig(Long productId);
}
