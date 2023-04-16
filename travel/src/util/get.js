import instance from './http'

instance.defaults.baseURL = "http://39.107.221.170:11111"

//获取景点列表
export function scenic(){
  return instance.get('/scenic/list')
}

//搜索景点
export function scenicCondition(key){
  return instance.get(`/scenic/condition/${key}`)
}

//获取景点信息
export function scenicDetail(name){
  return instance.get(`/scenic/${name}`)
}

//获取民宿列表
export function homestay(){
  return instance.get('/homestay/list')
}

//搜索民宿
export function homestayCondition(key){
  return instance.get(`/homestay/condition/${key}`)
}

//获取民宿信息
export function homestayDetail(name){
  return instance.get(`/homestay/${name}`)
}

//获取土特产列表
export function goods(){
  return instance.get('/goods/list')
}

//搜索土特产
export function goodsCondition(key){
  return instance.get(`/goods/condition/${key}`)
}

//获取土特产信息
export function goodsDetail(name){
  return instance.get(`/goods/${name}`)
}

//获取空闲车辆列表
export function taxi(){
  return instance.get('/taxi/list')
}

//搜索车辆
export function taxiCondition(key){
  return instance.get(`/taxi/condition/${key}`)
}

//获取车辆信息
export function taxiDetail(name){
  return instance.get(`/taxi/${name}`)
}

//获取全部评论
export function comment(type,articleId){
  return instance.get(`/comment/list?type=${type}&articleId=${articleId}`)
}

