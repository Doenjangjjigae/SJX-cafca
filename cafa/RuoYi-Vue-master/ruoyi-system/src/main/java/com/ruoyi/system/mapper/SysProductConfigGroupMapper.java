package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysProductConfigGroup;
import java.util.List;

public interface SysProductConfigGroupMapper {
    
    SysProductConfigGroup selectConfigGroupById(Long configGroupId);

    List<SysProductConfigGroup> selectConfigGroupsByProductId(Long productId);

    int insertConfigGroup(SysProductConfigGroup configGroup);

    int updateConfigGroup(SysProductConfigGroup configGroup);

    int deleteConfigGroupById(Long configGroupId);

    int deleteConfigGroupsByProductId(Long productId);
}
