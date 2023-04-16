// 全局拦截器,在进入需要用户权限的页面前校验是否已经登录
import router from './router'

router.beforeEach((to, from, next) => {
    let tokenStr = window.sessionStorage.getItem('token')
    // 判断路由是否设置相应校验用户权限
    if (to.meta.requireAuth) {//需要权限
        if (tokenStr === null || tokenStr === undefined || tokenStr === '') {
            if (from.name == null) {
                //此时，是在页面没有加载，直接在地址栏输入链接，进入需要登录验证的页面
                next("/login");
                return;
            }
            next("/login");
        } 
        
        else {//已经登录了，就不要重复登录
            if (to=="/login") {
                next(false)// 终止导航
            }
                next();
        }
    } else {//不需要权限  都放行
        next();
    }
});

