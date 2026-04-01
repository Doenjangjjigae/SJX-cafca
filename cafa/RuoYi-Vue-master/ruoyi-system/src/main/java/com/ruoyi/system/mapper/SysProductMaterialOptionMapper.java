package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysProductMaterialOption;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SysProductMaterialOptionMapper {
    
    List<SysProductMaterialOption> selectOptionsByProductMaterialId(Long productMaterialId);

    int insertOption(SysProductMaterialOption option);

    int batchInsertOptions(@Param("list") List<SysProductMaterialOption> list);

    int deleteOptionsByProductMaterialId(Long productMaterialId);

    int deleteOptionsByProductMaterialIds(@Param("ids") List<Long> ids);
}
