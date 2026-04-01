import request from '@/utils/request'

export function listProduct(query) {
  return request({
    url: '/system/product/list',
    method: 'get',
    params: query
  })
}

export function getProduct(productId) {
  return request({
    url: '/system/product/info/' + productId,
    method: 'get'
  })
}

export function addProduct(data) {
  return request({
    url: '/system/product/add',
    method: 'post',
    data: data
  })
}

export function updateProduct(data) {
  return request({
    url: '/system/product/edit',
    method: 'put',
    data: data
  })
}

export function delProduct(productId) {
  return request({
    url: '/system/product/remove/' + productId,
    method: 'delete'
  })
}

export function delProducts(productIds) {
  return request({
    url: '/system/product/remove',
    method: 'delete',
    data: productIds
  })
}

export function listMaterial(query) {
  return request({
    url: '/system/inventory/material/list',
    method: 'get',
    params: query
  })
}
