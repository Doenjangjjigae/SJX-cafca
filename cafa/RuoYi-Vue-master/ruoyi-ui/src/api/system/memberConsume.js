import request from '@/utils/request'

// 查询会员消费记录列表
export function listMemberConsume(query) {
  return request({
    url: '/system/member/consume/list',
    method: 'get',
    params: query
  })
}

// 根据会员ID查询消费记录列表
export function getConsumeByMemberId(memberId) {
  return request({
    url: `/system/member/consume/member/${memberId}`,
    method: 'get'
  })
}

// 新增会员消费记录
export function addMemberConsume(data) {
  return request({
    url: '/system/member/consume',
    method: 'post',
    data: data
  })
}

// 修改会员消费记录
export function updateMemberConsume(data) {
  return request({
    url: '/system/member/consume',
    method: 'put',
    data: data
  })
}

// 删除会员消费记录
export function delMemberConsume(id) {
  return request({
    url: `/system/member/consume/${id}`,
    method: 'delete'
  })
}
