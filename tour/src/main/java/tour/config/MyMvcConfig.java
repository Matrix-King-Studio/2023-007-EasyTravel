package tour.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tour.base.interceptor.JwtInterceptor;
import tour.base.utils.JwtUtils;

import javax.annotation.Resource;


@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Resource
    JwtInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
