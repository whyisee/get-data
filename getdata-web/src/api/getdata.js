import request from '@/utils/request'

export function getTaskList(data) {
  return request({
    url: '/configmain/search',
    method: 'post',
    data: data
  })
}
