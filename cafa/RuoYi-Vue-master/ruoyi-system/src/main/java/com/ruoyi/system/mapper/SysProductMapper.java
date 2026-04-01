package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysProduct;

import java.util.List;

public interface SysProductMapper {
    /**
     * 查询商品列表
     *
     * @param product 商品信息
     * @return 商品列表
     */
    List<SysProduct> selectProductList(SysProduct product);

    /**
     * 通过商品ID查询商品
     *
     * @param productId 商品ID
     * @return 商品信息
     */
    SysProduct selectProductById(Long productId);

    /**
     * 新增商品
     *
     * @param product 商品信息
     * @return 结果
     */
    int insertProduct(SysProduct product);

    /**
     * 修改商品
     *
     * @param product 商品信息
     * @return 结果
     */
    int updateProduct(SysProduct product);

    /**
     * 删除商品
     *
     * @param productId 商品ID
     * @return 结果
     */
    int deleteProductById(Long productId);

    /**
     * 批量删除商品
     *
     * @param productIds 需要删除的商品ID
     * @return 结果
     */
    int deleteProductByIds(Long[] productIds);
}