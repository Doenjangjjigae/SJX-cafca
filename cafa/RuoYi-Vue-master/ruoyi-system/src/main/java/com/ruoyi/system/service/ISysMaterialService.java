package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysMaterial;

import java.math.BigDecimal;
import java.util.List;

public interface ISysMaterialService {
    
    public SysMaterial selectSysMaterialById(Long materialId);

    public List<SysMaterial> selectSysMaterialList(SysMaterial sysMaterial);

    public int insertSysMaterial(SysMaterial sysMaterial);

    public int updateSysMaterial(SysMaterial sysMaterial);

    public int deleteSysMaterialById(Long materialId);

    public int deleteSysMaterialByIds(Long[] materialIds);

    public List<SysMaterial> selectLowStockMaterials();

    public int addStock(Long materialId, BigDecimal quantity, String operator, String remark);

    public int stockTake(Long materialId, BigDecimal quantity, String operator, String remark);

    public int setWarningThreshold(Long materialId, BigDecimal warningThreshold);

    public int deductStock(Long materialId, BigDecimal quantity, String operator, String remark);

    public int deductStockWithCheck(Long materialId, BigDecimal quantity, String operator, String remark);
}
