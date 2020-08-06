package com.fh.controller;

import com.fh.util.JWT;
import com.fh.util.JsonData;
import com.fh.util.RedisUse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("LoginController")
public class LoginController {
    @RequestMapping("selectCodeMessage")
    public JsonData selectCodeMessage(){
        String code="1111";
        RedisUse.set("iPhone_Code",code,3*60);
        return JsonData.getJsonSuccess("短信发送成功");
    }

   @RequestMapping("login")
    public JsonData login(String iPhone,String code){
       Map loginMap=new HashMap();
       //从redis中取出用户信息
       String iPhone_code = RedisUse.get("iPhone_Code");
       //如果手机号和验证码不等于空并且手机号验证码与前台接收到的验证码
       // 一样生成一个秘钥对应一个信息
        if(iPhone_code!=null && iPhone_code.equals(code)){
            //将手机号放到map中
            Map map=new HashMap();
            map.put("iPhone",iPhone);
            //对对象进行加密
            String sign = JWT.sign(map, 1000 * 60 * 60 * 24);
            String token = Base64.getEncoder().encodeToString((iPhone + "," + sign).getBytes());
            RedisUse.set(iPhone+"_syy",sign,60*30);
            loginMap.put("status",200);
            loginMap.put("message","登陆成功");
            loginMap.put("token",token);
        }else {
            loginMap.put("status",300);
            loginMap.put("message","用户不存在或者验证码不正确");
        }
       return JsonData.getJsonSuccess(loginMap);
   }
}
