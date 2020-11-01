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

export function getFlowId(data) {
  return request({
    url: '/common/seq',
    method: 'get'
  })
}

