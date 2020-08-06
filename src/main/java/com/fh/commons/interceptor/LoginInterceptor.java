package com.fh.commons.interceptor;

import com.fh.commons.exception.LoginException;
import com.fh.util.JWT;
import com.fh.util.RedisUse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Map;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取前台传过来的参数这个参数是存在sessionStorage中给header再从header中拿到的信息sign iPhone
        String token = request.getParameter("token");
        //判断头信息是否完整
        if(StringUtils.isEmpty(token)==true){
            String requestURI = request.getRequestURI();
            throw new LoginException("请求头信息不完整");
        }
        //使用jwt进行解密使用base64进行验签
        byte[] decode = Base64.getDecoder().decode(token);
        //因为 base64这个码是字节型要将他转成string类型
        String string=new String(decode);
        String[] split = string.split(",");
        if (split.length!=2){
            throw new Exception("数据被篡改");
        }
        String iPhone = split[0];
        String sign = split[1];

        //将获取到的秘钥进行解密
        Map user = JWT.unsign(sign, Map.class);
        //判断用户有没有登录
        if(user==null){
            throw new LoginException("用户没有登录");
        }
        if (user!=null){
            //取出秘钥
            String signString= RedisUse.get(iPhone+"_syy");
            if (!sign.equals(signString)){
                throw new LoginException("验证过期，请重新登录");
            }
        }
        //前面逻辑验证过了，设置redis的过时时间给它赋值
        RedisUse.set(user.get("iPhone")+"_syy",sign,60*30);
        request.setAttribute("user",user);
        return true;
    }

}
