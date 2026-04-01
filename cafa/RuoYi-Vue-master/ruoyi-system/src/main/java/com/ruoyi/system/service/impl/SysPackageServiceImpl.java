package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysPackage;
import com.ruoyi.system.domain.SysPackageItem;
import com.ruoyi.system.domain.SysProductConfigGroup;
import com.ruoyi.system.mapper.SysPackageMapper;
import com.ruoyi.system.mapper.SysPackageItemMapper;
import com.ruoyi.system.service.ISysPackageService;
import com.ruoyi.system.service.ISysProductConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPackageServiceImpl implements ISysPackageService {
    @Autowired
    private SysPackageMapper packageMapper;

    @Autowired
    private SysPackageItemMapper packageItemMapper;

    @Autowired
    private ISysProductConfigService productConfigService;

    @Override
    public List<SysPackage> selectPackageList(SysPackage pkg) {
        List<SysPackage> packages = packageMapper.selectPackageList(pkg);
        for (SysPackage p : packages) {
            List<SysPackageItem> items = packageItemMapper.selectItemsByPackageId(p.getPackageId());
            for (SysPackageItem item : items) {
                List<SysProductConfigGroup> configGroups = productConfigService.selectConfigGroupsByProductId(item.getProductId());
                item.setConfigGroups(configGroups);
            }
            p.setItems(items);
        }
        return packages;
    }

    @Override
    public SysPackage selectPackageById(Long packageId) {
        SysPackage pkg = packageMapper.selectPackageById(packageId);
        if (pkg != null) {
            List<SysPackageItem> items = packageItemMapper.selectItemsByPackageId(packageId);
            for (SysPackageItem item : items) {
                List<SysProductConfigGroup> configGroups = productConfigService.selectConfigGroupsByProductId(item.getProductId());
                item.setConfigGroups(configGroups);
            }
            pkg.setItems(items);
        }
        return pkg;
    }

    @Override
    public int insertPackage(SysPackage pkg) {
        pkg.setCreateBy(SecurityUtils.getUsername());
        pkg.setCreateTime(DateUtils.getNowDate());
        pkg.setUpdateBy(SecurityUtils.getUsername());
        pkg.setUpdateTime(DateUtils.getNowDate());
        int result = packageMapper.insertPackage(pkg);
        
        if (pkg.getItems() != null && !pkg.getItems().isEmpty()) {
            for (SysPackageItem item : pkg.getItems()) {
                item.setPackageId(pkg.getPackageId());
                packageItemMapper.insertPackageItem(item);
            }
        }
        
        return result;
    }

    @Override
    public int updatePackage(SysPackage pkg) {
        pkg.setUpdateBy(SecurityUtils.getUsername());
        pkg.setUpdateTime(DateUtils.getNowDate());
        
        packageItemMapper.deleteItemsByPackageId(pkg.getPackageId());
        if (pkg.getItems() != null && !pkg.getItems().isEmpty()) {
            for (SysPackageItem item : pkg.getItems()) {
                item.setPackageId(pkg.getPackageId());
                packageItemMapper.insertPackageItem(item);
            }
        }
        
        return packageMapper.updatePackage(pkg);
    }

    @Override
    public int deletePackageById(Long packageId) {
        packageItemMapper.deleteItemsByPackageId(packageId);
        return packageMapper.deletePackageById(packageId);
    }

    @Override
    public int deletePackageByIds(Long[] packageIds) {
        for (Long packageId : packageIds) {
            packageItemMapper.deleteItemsByPackageId(packageId);
        }
        return packageMapper.deletePackageByIds(packageIds);
    }
}