package com.fh.commons.exception;

import com.fh.util.JsonData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//springMVC提供的全局处理异常
public class ControllerException {

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public JsonData test(){
        return JsonData.getJsonError("参数为0");
    }

    @ExceptionHandler(LoginException.class) //
    @ResponseBody
    public JsonData handleNoLoginException(LoginException e){
        return JsonData.getJsonError(1000,e.getMessage());
    }


    @ExceptionHandler(CountException.class) //
    @ResponseBody
    public JsonData handleCountException(CountException e){
        return JsonData.getJsonError(2000,e.getMessage());
    }


    /**
     * 处理所有不可知异常
     *
     * @param e 异常
     * @return json结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonData handleException(Exception e) {

        return JsonData.getJsonError(e.getMessage());
    }



}
