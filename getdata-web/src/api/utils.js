import request from '@/utils/request'

export function getSeq() {
  return request({
    url: '/common/seq',
    method: 'get'
  })
}
