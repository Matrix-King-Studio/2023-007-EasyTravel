package tour.base.interceptor;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tour.base.utils.ApiResponse;
import tour.base.utils.JwtUtils;
import tour.base.utils.StringUtil;
import tour.base.utils.UserThreadLocal;
import tour.entity.User;
import tour.service.UserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhdpzz
 * @description:
 * @date 2023年04月03日 16:55
 */
@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Autowired
    UserThreadLocal userThreadLocal;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("GET".equals(request.getMethod() )){
            return true;
        }
        log.info(request.getMethod());
        String token = request.getHeader("token");
        if (StringUtil.isNullString(token)){
            throw new RuntimeException("请登录");
        }
        Claims claim = jwtUtils.getClaimByToken(token);
        if (claim == null){
            throw new RuntimeException("token错误，请重新登录");
        }
        String username = claim.getSubject();
        User user = userService.getUserByUsername(username);

        if (jwtUtils.isTokenExpired(claim)){
            throw new RuntimeException("token已超时，请重新登录");
        }
        userThreadLocal.set(user.getId());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
