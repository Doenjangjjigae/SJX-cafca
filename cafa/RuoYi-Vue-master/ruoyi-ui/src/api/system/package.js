import request from '@/utils/request'

// 查询套餐列表
export function listPackage(query) {
  return request({
    url: '/system/package/list',
    method: 'get',
    params: query
  })
}

// 查询套餐详细
export function getPackage(packageId) {
  return request({
    url: '/system/package/info/' + packageId,
    method: 'get'
  })
}

// 新增套餐
export function addPackage(data) {
  return request({
    url: '/system/package/add',
    method: 'post',
    data: data
  })
}

// 修改套餐
export function updatePackage(data) {
  return request({
    url: '/system/package/edit',
    method: 'put',
    data: data
  })
}

// 删除套餐
export function delPackage(packageId) {
  return request({
    url: '/system/package/remove/' + packageId,
    method: 'delete'
  })
}

// 批量删除套餐
export function delPackages(packageIds) {
  return request({
    url: '/system/package/remove',
    method: 'delete',
    data: packageIds
  })
}