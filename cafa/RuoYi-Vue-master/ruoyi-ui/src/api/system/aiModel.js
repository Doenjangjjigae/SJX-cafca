import request from '@/utils/request'

export function getAIModelList() {
  return request({
    url: '/system/ai/model/list',
    method: 'get'
  })
}

export function getAIModelInfo(modelId) {
  return request({
    url: `/system/ai/model/info/${modelId}`,
    method: 'get'
  })
}

export function addAIModel(data) {
  return request({
    url: '/system/ai/model/add',
    method: 'post',
    data: data
  })
}

export function updateAIModel(data) {
  return request({
    url: '/system/ai/model/edit',
    method: 'put',
    data: data
  })
}

export function deleteAIModel(modelId) {
  return request({
    url: `/system/ai/model/delete/${modelId}`,
    method: 'delete'
  })
}

export function enableAIModel(modelId) {
  return request({
    url: `/system/ai/model/enable/${modelId}`,
    method: 'put'
  })
}

export default {
  getAIModelList,
  getAIModelInfo,
  addAIModel,
  updateAIModel,
  deleteAIModel,
  enableAIModel
}