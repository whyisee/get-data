import request from '@/utils/request'

export function getTaskList(data) {
  return request({
    url: '/configmain/search',
    method: 'post',
    data: data
  })
}

export function createTask(data) {
  return request({
    url: '/configmain',
    method: 'post',
    data: data
  })
}

export function updateTask(data) {
  return request({
    url: '/configmain/',
    method: 'put',
    data: data
  })
}
export function deleteTask(data) {
  return request({
    url: '/configmain/' + data.taskId,
    method: 'delete',
    data: data
  })
}

export function downloadTask(data) {
  return request({
    url: '/file/download' + '?fileName=' + data.taskId,
    method: 'get'
  })
}

export function getFlowId(data) {
  return request({
    url: '/common/seq',
    method: 'get'
  })
}

export function getTask(data) {
  return request({
    url: '/configmain/' + data,
    method: 'get',
    data: data
  })
}
export function getConfigFlow(data) {
  return request({
    url: '/configflow/search',
    method: 'post',
    data: data
  })
}
