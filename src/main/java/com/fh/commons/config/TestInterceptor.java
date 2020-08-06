package com.fh.commons.config;

import com.fh.commons.interceptor.KuaYuInterceptor;
import com.fh.commons.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
//实现WebMvcConfigurer
public class TestInterceptor implements WebMvcConfigurer {

    @Override
    //重写
    public void addInterceptors(InterceptorRegistry registry) {
        //实现注册登录
        registry.addInterceptor(new KuaYuInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(
                Arrays.asList(
                        "/LoginController/**",
                        "/TypeController/**",
                        "/GoodsController/**"


                )
        );

    }

  /*  @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        // 解决 SWAGGER 404报错
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }*/
}
