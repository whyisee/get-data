import request from '@/utils/request'

export function getDataSourceList(data) {
  return request({
    url: '/datasource/search',
    method: 'post',
    data: data
  })
}

export function getUserTroopList(data) {
  return request({
    url: '/usertroop/search',
    method: 'post',
    data: data
  })
}

export function createTaskInit() {
  const data = {}
  return request.all([getDataSourceList(data), getUserTroopList(data)]).then(request.spread(function(...resList) {
    // 两个请求现在都执行完成
    // 暂时无校验,后期需加上
    return resList
  }))
}
