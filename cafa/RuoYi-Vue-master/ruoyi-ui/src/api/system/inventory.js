import request from '@/utils/request'

// 原料信息相关API
export function listMaterial(query) {
  return request({
    url: '/system/inventory/material/list',
    method: 'get',
    params: query
  })
}

export function getMaterial(materialId) {
  return request({
    url: `/system/inventory/material/${materialId}`,
    method: 'get'
  })
}

export function addMaterial(data) {
  return request({
    url: '/system/inventory/material',
    method: 'post',
    data: data
  })
}

export function updateMaterial(data) {
  return request({
    url: '/system/inventory/material',
    method: 'put',
    data: data
  })
}

export function delMaterial(materialId) {
  return request({
    url: `/system/inventory/material/${materialId}`,
    method: 'delete'
  })
}

export function setWarningThreshold(materialId, warningThreshold) {
  return request({
    url: '/system/inventory/material/setThreshold',
    method: 'put',
    params: { materialId, warningThreshold }
  })
}

// 库存操作相关API
export function listInventoryRecord(query) {
  return request({
    url: '/system/inventory/operation/list',
    method: 'get',
    params: query
  })
}

export function addStock(data) {
  return request({
    url: '/system/inventory/operation/addStock',
    method: 'post',
    params: data
  })
}

export function stockTake(data) {
  return request({
    url: '/system/inventory/operation/stockTake',
    method: 'post',
    params: data
  })
}

// 库存预警相关API
export function listLowStockMaterials() {
  return request({
    url: '/system/inventory/warning/list',
    method: 'get'
  })
}