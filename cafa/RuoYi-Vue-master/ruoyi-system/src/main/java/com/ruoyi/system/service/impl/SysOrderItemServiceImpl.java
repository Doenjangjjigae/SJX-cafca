package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysMaterial;
import com.ruoyi.system.domain.SysOrderItem;
import com.ruoyi.system.mapper.SysMaterialMapper;
import com.ruoyi.system.mapper.SysOrderItemMapper;
import com.ruoyi.system.service.ISysOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SysOrderItemServiceImpl implements ISysOrderItemService {

    @Autowired
    private SysOrderItemMapper orderItemMapper;

    @Autowired
    private SysMaterialMapper materialMapper;

    @Override
    public List<SysOrderItem> selectOrderItemList(SysOrderItem orderItem) {
        return orderItemMapper.selectOrderItemList(orderItem);
    }

    @Override
    public SysOrderItem selectOrderItemById(Long id) {
        return orderItemMapper.selectOrderItemByOrderId(id);
    }

    @Override
    public List<SysOrderItem> selectOrderItemByOrderId(Long orderId) {
        SysOrderItem query = new SysOrderItem();
        query.setOrderId(orderId);
        List<SysOrderItem> orderItems = orderItemMapper.selectOrderItemList(query);
        
        // 解析materialOptions和configOptions字段
        for (SysOrderItem item : orderItems) {
            // 解析原料选项
            if (item.getMaterialOptions() != null && !item.getMaterialOptions().isEmpty()) {
                try {
                    com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    java.util.List<com.ruoyi.system.domain.OrderItemMaterialOption> options = objectMapper.readValue(
                        item.getMaterialOptions(),
                        new com.fasterxml.jackson.core.type.TypeReference<java.util.List<com.ruoyi.system.domain.OrderItemMaterialOption>>() {}
                    );
                    item.setSelectedOptions(options);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            // 解析配置选项
            if (item.getConfigOptions() != null && !item.getConfigOptions().isEmpty()) {
                try {
                    com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    java.util.List<com.ruoyi.system.domain.OrderItemConfigOption> options = objectMapper.readValue(
                        item.getConfigOptions(),
                        new com.fasterxml.jackson.core.type.TypeReference<java.util.List<com.ruoyi.system.domain.OrderItemConfigOption>>() {}
                    );
                    item.setConfigOptionList(options);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        return orderItems;
    }

    @Override
    @Transactional
    public int insertOrderItem(SysOrderItem orderItem) {
        int result = orderItemMapper.insertOrderItem(orderItem);
        
        // 实时扣减库存
        // 根据商品名称查找对应的物料信息
        SysMaterial material = new SysMaterial();
        material.setMaterialName(orderItem.getProductName());
        List<SysMaterial> materials = materialMapper.selectSysMaterialList(material);
        if (!materials.isEmpty()) {
            SysMaterial existingMaterial = materials.get(0);
            // 扣减库存
            BigDecimal currentStock = existingMaterial.getCurrentStock();
            BigDecimal quantity = new BigDecimal(orderItem.getQuantity());
            if (currentStock.compareTo(quantity) >= 0) {
                existingMaterial.setCurrentStock(currentStock.subtract(quantity));
                materialMapper.updateSysMaterial(existingMaterial);
            }
        }
        
        return result;
    }

    @Override
    public int updateOrderItem(SysOrderItem orderItem) {
        return orderItemMapper.updateOrderItemById(orderItem);
    }

    @Override
    public int deleteOrderItemById(Long id) {
        return orderItemMapper.deleteOrderItemByOrderId(id);
    }

    @Override
    public int deleteOrderItemByIds(Long[] ids) {
        return orderItemMapper.deleteOrderItemByIds(ids);
    }
}
