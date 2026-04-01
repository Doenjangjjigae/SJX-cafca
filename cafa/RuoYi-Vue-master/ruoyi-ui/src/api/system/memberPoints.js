import request from '@/utils/request'

// 查询会员积分记录列表
export function listMemberPoints(query) {
  return request({
    url: '/system/member/points/list',
    method: 'get',
    params: query
  })
}

// 根据会员ID查询积分记录列表
export function getPointsByMemberId(memberId) {
  return request({
    url: `/system/member/points/member/${memberId}`,
    method: 'get'
  })
}

// 手动录入会员积分
export function addManualPoints(memberId, points, remark) {
  return request({
    url: '/system/member/points/manual',
    method: 'post',
    params: { memberId, points, remark }
  })
}

// 删除会员积分记录
export function delMemberPoints(id) {
  return request({
    url: `/system/member/points/${id}`,
    method: 'delete'
  })
}
