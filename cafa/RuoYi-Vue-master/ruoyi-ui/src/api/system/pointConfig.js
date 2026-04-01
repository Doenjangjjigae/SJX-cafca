import request from '@/utils/request'

// 查询积分配置列表
export function listPointConfig(query) {
  return request({
    url: '/system/pointConfig/list',
    method: 'get',
    params: query
  })
}

// 查询积分配置详细
export function getPointConfig(configId) {
  return request({
    url: '/system/pointConfig/' + configId,
    method: 'get'
  })
}

// 新增积分配置
export function addPointConfig(data) {
  return request({
    url: '/system/pointConfig',
    method: 'post',
    data: data
  })
}

// 修改积分配置
export function updatePointConfig(data) {
  return request({
    url: '/system/pointConfig',
    method: 'put',
    data: data
  })
}

// 删除积分配置
export function delPointConfig(configId) {
  return request({
    url: '/system/pointConfig/' + configId,
    method: 'delete'
  })
}

// 获取当前积分配置
export function getCurrentPointConfig() {
  return request({
    url: '/system/pointConfig/current',
    method: 'get'
  })
}