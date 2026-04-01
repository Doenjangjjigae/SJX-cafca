import request from '@/utils/request'

// 收银管理相关API

// 根据会员电话查询会员信息
export function getMemberByPhone(phone) {
  return request({
    url: `/system/cashier/member/info/${phone}`,
    method: 'get'
  })
}

// 查询会员可用优惠券
export function getMemberCoupons(memberId) {
  return request({
    url: `/system/cashier/member/coupons/${memberId}`,
    method: 'get'
  })
}

// 计算优惠金额
export function calculateDiscount(data) {
  return request({
    url: '/system/cashier/calculate',
    method: 'post',
    data
  })
}

// 收银操作
export function checkout(data) {
  return request({
    url: '/system/cashier/checkout',
    method: 'post',
    data
  })
}
