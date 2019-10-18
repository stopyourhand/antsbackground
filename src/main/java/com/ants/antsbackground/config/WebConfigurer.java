package com.ants.antsbackground.config;

import com.ants.antsbackground.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 * @Author czd
 * @Date:createed in 2019/10/14
 * @Version: V1.0
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {



//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/index").setViewName("login");
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        System.out.println("config==============");
        registry.addInterceptor(new LoginHandlerInterceptor())
                //添加拦截请求页面
                .addPathPatterns("/**")
                //添加不进行拦截的页面
                .excludePathPatterns("/login.html");
    }
}
