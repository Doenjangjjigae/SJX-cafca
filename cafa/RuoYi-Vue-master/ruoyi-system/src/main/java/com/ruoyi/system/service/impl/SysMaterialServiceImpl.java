package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysInventoryRecord;
import com.ruoyi.system.domain.SysMaterial;
import com.ruoyi.system.mapper.SysInventoryRecordMapper;
import com.ruoyi.system.mapper.SysMaterialMapper;
import com.ruoyi.system.service.ISysMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class SysMaterialServiceImpl implements ISysMaterialService
{
    @Autowired
    private SysMaterialMapper sysMaterialMapper;

    @Autowired
    private SysInventoryRecordMapper sysInventoryRecordMapper;

    @Override
    public SysMaterial selectSysMaterialById(Long materialId)
    {
        return sysMaterialMapper.selectSysMaterialById(materialId);
    }

    @Override
    public List<SysMaterial> selectSysMaterialList(SysMaterial sysMaterial)
    {
        return sysMaterialMapper.selectSysMaterialList(sysMaterial);
    }

    @Override
    public int insertSysMaterial(SysMaterial sysMaterial)
    {
        if (sysMaterial.getCurrentStock() == null) {
            sysMaterial.setCurrentStock(BigDecimal.ZERO);
        }
        if (sysMaterial.getWarningThreshold() == null) {
            sysMaterial.setWarningThreshold(BigDecimal.ZERO);
        }
        if (sysMaterial.getStatus() == null) {
            sysMaterial.setStatus("0");
        }
        return sysMaterialMapper.insertSysMaterial(sysMaterial);
    }

    @Override
    public int updateSysMaterial(SysMaterial sysMaterial)
    {
        return sysMaterialMapper.updateSysMaterial(sysMaterial);
    }

    @Override
    public int deleteSysMaterialById(Long materialId)
    {
        return sysMaterialMapper.deleteSysMaterialById(materialId);
    }

    @Override
    public int deleteSysMaterialByIds(Long[] materialIds)
    {
        return sysMaterialMapper.deleteSysMaterialByIds(materialIds);
    }

    @Override
    public List<SysMaterial> selectLowStockMaterials()
    {
        return sysMaterialMapper.selectLowStockMaterials();
    }

    @Override
    @Transactional
    public int addStock(Long materialId, BigDecimal quantity, String operator, String remark)
    {
        SysMaterial material = sysMaterialMapper.selectSysMaterialById(materialId);
        if (material == null) {
            return 0;
        }

        BigDecimal beforeStock = material.getCurrentStock();
        BigDecimal afterStock = beforeStock.add(quantity);

        material.setCurrentStock(afterStock);
        sysMaterialMapper.updateMaterialStock(material);

        SysInventoryRecord record = new SysInventoryRecord();
        record.setMaterialId(materialId);
        record.setOperationType("1");
        record.setQuantity(quantity);
        record.setBeforeStock(beforeStock);
        record.setAfterStock(afterStock);
        record.setOperator(operator);
        record.setOperationTime(new Date());
        record.setRemark(remark);
        return sysInventoryRecordMapper.insertSysInventoryRecord(record);
    }

    @Override
    @Transactional
    public int stockTake(Long materialId, BigDecimal quantity, String operator, String remark)
    {
        SysMaterial material = sysMaterialMapper.selectSysMaterialById(materialId);
        if (material == null) {
            return 0;
        }

        BigDecimal beforeStock = material.getCurrentStock();
        BigDecimal afterStock = quantity;

        material.setCurrentStock(afterStock);
        sysMaterialMapper.updateMaterialStock(material);

        SysInventoryRecord record = new SysInventoryRecord();
        record.setMaterialId(materialId);
        record.setOperationType("2");
        record.setQuantity(afterStock.subtract(beforeStock));
        record.setBeforeStock(beforeStock);
        record.setAfterStock(afterStock);
        record.setOperator(operator);
        record.setOperationTime(new Date());
        record.setRemark(remark);
        return sysInventoryRecordMapper.insertSysInventoryRecord(record);
    }

    @Override
    public int setWarningThreshold(Long materialId, BigDecimal warningThreshold)
    {
        SysMaterial material = new SysMaterial();
        material.setMaterialId(materialId);
        material.setWarningThreshold(warningThreshold);
        return sysMaterialMapper.updateSysMaterial(material);
    }

    @Override
    @Transactional
    public int deductStock(Long materialId, BigDecimal quantity, String operator, String remark)
    {
        SysMaterial material = sysMaterialMapper.selectSysMaterialById(materialId);
        if (material == null) {
            return 0;
        }

        BigDecimal beforeStock = material.getCurrentStock();
        BigDecimal afterStock = beforeStock.subtract(quantity);
        if (afterStock.compareTo(BigDecimal.ZERO) < 0) {
            afterStock = BigDecimal.ZERO;
        }

        material.setCurrentStock(afterStock);
        sysMaterialMapper.updateMaterialStock(material);

        SysInventoryRecord record = new SysInventoryRecord();
        record.setMaterialId(materialId);
        record.setOperationType("3");
        record.setQuantity(quantity.negate());
        record.setBeforeStock(beforeStock);
        record.setAfterStock(afterStock);
        record.setOperator(operator);
        record.setOperationTime(new Date());
        record.setRemark(remark);
        return sysInventoryRecordMapper.insertSysInventoryRecord(record);
    }

    @Override
    @Transactional
    public int deductStockWithCheck(Long materialId, BigDecimal quantity, String operator, String remark)
    {
        SysMaterial material = sysMaterialMapper.selectSysMaterialById(materialId);
        if (material == null) {
            throw new RuntimeException("原料不存在，ID: " + materialId);
        }

        BigDecimal beforeStock = material.getCurrentStock();
        if (beforeStock.compareTo(quantity) < 0) {
            throw new RuntimeException("原料[" + material.getMaterialName() + "]库存不足，当前库存: " + beforeStock + "，需要: " + quantity);
        }

        BigDecimal afterStock = beforeStock.subtract(quantity);

        material.setCurrentStock(afterStock);
        sysMaterialMapper.updateMaterialStock(material);

        SysInventoryRecord record = new SysInventoryRecord();
        record.setMaterialId(materialId);
        record.setOperationType("3");
        record.setQuantity(quantity.negate());
        record.setBeforeStock(beforeStock);
        record.setAfterStock(afterStock);
        record.setOperator(operator);
        record.setOperationTime(new Date());
        record.setRemark(remark);
        return sysInventoryRecordMapper.insertSysInventoryRecord(record);
    }
}
