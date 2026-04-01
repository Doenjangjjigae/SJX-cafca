import request from '@/utils/request'

export function listTable(query) {
  return request({
    url: '/system/table/list',
    method: 'get',
    params: query
  })
}

export function getTable(tableId) {
  return request({
    url: '/system/table/' + tableId,
    method: 'get'
  })
}

export function addTable(data) {
  return request({
    url: '/system/table',
    method: 'post',
    data: data
  })
}

export function updateTable(data) {
  return request({
    url: '/system/table',
    method: 'put',
    data: data
  })
}

export function deleteTable(tableIds) {
  return request({
    url: '/system/table/' + tableIds,
    method: 'delete'
  })
}

export function updateTableStatus(tableId, status) {
  return request({
    url: '/system/table/status',
    method: 'put',
    params: { tableId, status }
  })
}
