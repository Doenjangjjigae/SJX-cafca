package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysPackageItem;
import java.util.List;

public interface SysPackageItemMapper {
    
    List<SysPackageItem> selectItemsByPackageId(Long packageId);

    int insertPackageItem(SysPackageItem item);

    int deleteItemsByPackageId(Long packageId);
}
