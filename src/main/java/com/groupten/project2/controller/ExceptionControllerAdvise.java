package com.groupten.project2.controller;


import com.groupten.project2.bean.vo.BaseRespVo;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionControllerAdvise {

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public BaseRespVo handleAuthorException(Exception e){
        return BaseRespVo.fail(506,"权限不够");
    }
}
