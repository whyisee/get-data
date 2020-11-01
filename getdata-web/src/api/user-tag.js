import request from '@/utils/request'

export function getSourceTagList(data) {
  return request({
    url: '/tagconfig/search',
    method: 'post',
    data: data
  })
}
