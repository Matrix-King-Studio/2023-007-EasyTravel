import instance from './http'

instance.defaults.baseURL = "http://39.107.221.170:11111"

//删除订单
export function deleteOrder(id){
  return instance.delete('/indent/', {
    data:{	
      id
    }
  })
}

//删除订单
export function deleteComment(info){
  return instance.delete('/comment/', {
    data:{	
      ...info
    }
  })
}

