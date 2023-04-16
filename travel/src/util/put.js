import instance from './http'

instance.defaults.baseURL = "http://39.107.221.170:11111"

//修改景点订单
export function scenicChange(data){
  return instance.put('/indent/scenic',data)
}

//修改民宿订单
export function homestayChange(data){
  return instance.put('/indent/homestay',data)
}

//修改特产订单
export function goodsChange(data){
  return instance.put('/indent/goods',data)
}

//修改拼车订单
export function taxiChange(data){
  return instance.put('/indent/taxi',data)
}


