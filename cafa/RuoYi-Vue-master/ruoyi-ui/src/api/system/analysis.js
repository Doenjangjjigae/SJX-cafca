import request from '@/utils/request'

// 格式化日期为yyyy-MM-dd HH:mm:ss
function formatDate(date) {
  if (!date) return ''
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  const seconds = String(date.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

export function getSalesAnalysis(type, beginTime, endTime) {
  return request({
    url: '/system/analysis/sales',
    method: 'get',
    params: {
      type,
      beginTime: formatDate(beginTime),
      endTime: formatDate(endTime)
    }
  })
}

export function getOrderAnalysis(type, beginTime, endTime) {
  return request({
    url: '/system/analysis/orders',
    method: 'get',
    params: {
      type,
      beginTime: formatDate(beginTime),
      endTime: formatDate(endTime)
    }
  })
}

export function getTopProducts(limit, beginTime, endTime) {
  return request({
    url: '/system/analysis/topProducts',
    method: 'get',
    params: {
      limit,
      beginTime: formatDate(beginTime),
      endTime: formatDate(endTime)
    }
  })
}
