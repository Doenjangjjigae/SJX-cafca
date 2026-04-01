package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysOrderPackageItem;
import com.ruoyi.system.mapper.SysOrderPackageItemMapper;
import com.ruoyi.system.service.ISysOrderPackageItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysOrderPackageItemServiceImpl implements ISysOrderPackageItemService {

    @Autowired
    private SysOrderPackageItemMapper orderPackageItemMapper;

    @Override
    public List<SysOrderPackageItem> selectOrderPackageItemList(SysOrderPackageItem orderPackageItem) {
        return orderPackageItemMapper.selectOrderPackageItemList(orderPackageItem);
    }

    @Override
    public SysOrderPackageItem selectOrderPackageItemById(Long id) {
        return orderPackageItemMapper.selectOrderPackageItemById(id);
    }

    @Override
    public List<SysOrderPackageItem> selectOrderPackageItemByOrderId(Long orderId) {
        return orderPackageItemMapper.selectOrderPackageItemByOrderId(orderId);
    }

    @Override
    public int insertOrderPackageItem(SysOrderPackageItem orderPackageItem) {
        return orderPackageItemMapper.insertOrderPackageItem(orderPackageItem);
    }

    @Override
    public int updateOrderPackageItem(SysOrderPackageItem orderPackageItem) {
        return orderPackageItemMapper.updateOrderPackageItem(orderPackageItem);
    }

    @Override
    public int deleteOrderPackageItemById(Long id) {
        return orderPackageItemMapper.deleteOrderPackageItemById(id);
    }

    @Override
    public int deleteOrderPackageItemByIds(Long[] ids) {
        return orderPackageItemMapper.deleteOrderPackageItemByIds(ids);
    }
}
