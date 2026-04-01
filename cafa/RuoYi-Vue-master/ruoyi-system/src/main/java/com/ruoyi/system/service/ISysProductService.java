package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysProduct;

import java.util.List;

public interface ISysProductService {
    
    List<SysProduct> selectProductList(SysProduct product);

    SysProduct selectProductById(Long productId);

    SysProduct selectProductWithMaterials(Long productId);

    int insertProduct(SysProduct product);

    int updateProduct(SysProduct product);

    int deleteProductById(Long productId);

    int deleteProductByIds(Long[] productIds);
}
