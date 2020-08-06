package com.fh.commons.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KuaYuInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置response
        // 允许跨域访问的域名：若有端口需写全（协议+域名+端口），若没有端口末尾不用加'/'
        //response.setHeader("Access-Control-Allow-Origin", "");
        //获取请求的域名
        String yuming = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", yuming);
        response.setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials","true");
        //当客户端修改了头信息，发起两个请求，第一个是预请求，（option是否运需修改头信息）发起真正的请求
        String method = request.getMethod();
        if (method.equalsIgnoreCase("options")){
          //允许修改头信息添加一个name属性s
            response.setHeader("Access-Control-Allow-Headers","token");
            //判断预请求是不是options请求如果是就直接return false 处理完后前台会自动发送真实的请求过来
            return false;
        }
        /*//从header里边去取数据
        String token = request.getHeader("token");*/
        return true;
    }
}
