//检查手机号
export function checkPhone(phone){
  const reg = /^(?:(?:\+|00)86)?1[3-9]\d{9}$/
  return reg.test(phone)
}


