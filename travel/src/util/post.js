import instance from './http'

instance.defaults.baseURL = "http://39.107.221.170:11111"

//登录
export function login(userInfo){
  return instance.post('/user/login',userInfo)
}

//注册
export function register(userInfo){
  return instance.post('/user/register',userInfo)
}

//添加景点订单
export function addScenicOrder(data){
  return instance.post('/indent/scenic/',data)
}

//添加民宿订单
export function addHomestayOrder(data){
  return instance.post('/indent/homestay/',data)
}

//添加商品订单
export function addGoodsOrder(data){
  return instance.post('/indent/goods/',data)
}

//添加拼车订单
export function addTaxiOrder(data){
  return instance.post('/indent/taxi/',data)
}

//点赞 / 取消点赞
export function commentLove(data){
  return instance.post('/comment/love',data)
}

//发布评论
export function commentSend(data){
  return instance.post('/comment/send',data)
}

//获取订单列表
export function orderList(){
  return instance.post("/indent/list")
}

