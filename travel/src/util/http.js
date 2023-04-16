import axios from "axios";
import { ElMessage } from "element-plus";
axios.defaults.withCredentials = true;
var instance = axios.create({
  timeout: 1000 * 12,
  withCredentials: true,
  crossDomain: true,
});

  instance.interceptors.request.use(
    (config) => {
      if(sessionStorage.getItem('token')){
          config.headers.token = sessionStorage.getItem('token')
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

instance.interceptors.response.use(
  (res) => {
    res.data.token?window.sessionStorage.setItem('token',res.data.token):''

    if (res.status === 200) {
      return Promise.resolve(res.data);
    } else {
      ElMessage.closeAll();
      ElMessage.error("网络请求错误！");
      return Promise.reject(res);
    }
  },
  (error) => {
    ElMessage.closeAll();
    ElMessage.error("网络请求错误！");
    const { response } = error;
    if (response) {
      return Promise.reject(response);
    } else {
      return Promise.reject(error);
    }
  }
);
export default instance;
