import request from '@/utils/request'

// 查询优惠券列表
export function listCoupon(query) {
  return request({
    url: '/system/coupon/list',
    method: 'get',
    params: query
  })
}

// 查询优惠券详细
export function getCoupon(couponId) {
  return request({
    url: '/system/coupon/' + couponId,
    method: 'get'
  })
}

// 新增优惠券
export function addCoupon(data) {
  return request({
    url: '/system/coupon',
    method: 'post',
    data: data
  })
}

// 修改优惠券
export function updateCoupon(data) {
  return request({
    url: '/system/coupon',
    method: 'put',
    data: data
  })
}

// 删除优惠券
export function delCoupon(couponId) {
  return request({
    url: '/system/coupon/' + couponId,
    method: 'delete'
  })
}

// 批量发布优惠券
export function publishCoupon(data) {
  return request({
    url: '/system/coupon/publish',
    method: 'put',
    data: data
  })
}

// 发放优惠券给所有会员
export function issueCoupon(couponId) {
  return request({
    url: '/system/coupon/issue/' + couponId,
    method: 'post'
  })
}

// 批量发放优惠券给所有会员
export function batchIssueCoupon(couponIds) {
  return request({
    url: '/system/coupon/issue',
    method: 'post',
    data: couponIds
  })
}

// 查询已发布的优惠券列表（用于收银页面下拉框）
export function getPublishedCoupons() {
  return request({
    url: '/system/coupon/published',
    method: 'get'
  })
}