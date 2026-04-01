package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysProductMaterial;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SysProductMaterialMapper {
    
    List<SysProductMaterial> selectProductMaterialByProductId(Long productId);

    SysProductMaterial selectProductMaterialById(Long id);

    int insertProductMaterial(SysProductMaterial productMaterial);

    int updateProductMaterial(SysProductMaterial productMaterial);

    int deleteProductMaterialById(Long id);

    int deleteProductMaterialByProductId(Long productId);

    int batchInsertProductMaterial(@Param("list") List<SysProductMaterial> list);
}
