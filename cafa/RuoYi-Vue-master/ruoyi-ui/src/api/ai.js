import request from '@/utils/request'

export function askAI(data) {
  return request({
    url: '/system/ai/ask',
    method: 'post',
    data: data
  })
}

export default {
  ask: askAI
}