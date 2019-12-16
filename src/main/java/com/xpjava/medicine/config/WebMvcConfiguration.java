package com.xpjava.medicine.config;

import com.xpjava.medicine.interceptors.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 帅鹏
 * @create 2019-11-04 14:25
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/medicine/user/**")
                .excludePathPatterns("/verification/**")
                .excludePathPatterns("/img/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/echarts/**")
                .excludePathPatterns("/icon/**")
                .excludePathPatterns("/jquery/**")
                .excludePathPatterns("/layui/**")
                .excludePathPatterns("/error");
    }
}
