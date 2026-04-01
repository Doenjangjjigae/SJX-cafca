import request from '@/utils/request'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/system/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrder(orderId) {
  return request({
    url: '/system/order/info/' + orderId,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/system/order/add',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/system/order/edit',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(orderId) {
  return request({
    url: '/system/order/remove/' + orderId,
    method: 'delete'
  })
}

// 批量删除订单
export function delOrders(orderIds) {
  return request({
    url: '/system/order/remove',
    method: 'delete',
    data: orderIds
  })
}

// 获取订单详情
export function getOrderDetail(orderId) {
  return request({
    url: '/system/order/detail/' + orderId,
    method: 'get'
  })
}