package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysProductConfigOption;
import java.util.List;

public interface SysProductConfigOptionMapper {
    
    SysProductConfigOption selectConfigOptionById(Long configOptionId);

    List<SysProductConfigOption> selectConfigOptionsByGroupId(Long configGroupId);

    int insertConfigOption(SysProductConfigOption configOption);

    int updateConfigOption(SysProductConfigOption configOption);

    int deleteConfigOptionById(Long configOptionId);

    int deleteConfigOptionsByGroupId(Long configGroupId);
}
