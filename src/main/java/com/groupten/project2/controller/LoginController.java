package com.groupten.project2.controller;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.groupten.project2.bean.AdminInfo;
import com.groupten.project2.bean.vo.BaseRespVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/auth")
public class LoginController {

    @RequestMapping("login")
    public BaseRespVo login(@RequestBody Map map){
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken((String) map.get("username"),(String) map.get("password")));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return BaseRespVo.fail();
        }
        Serializable id = subject.getSession().getId();
        return BaseRespVo.ok(id);
    }

    @RequestMapping("info")
    public BaseRespVo info(String token){
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAvatar("");
        adminInfo.setName("admin");
        List<String> roles = new ArrayList<>();
        String[] perms = new String[]{"*"};
        roles.add("超级管理员");
        adminInfo.setRoles(roles);
        adminInfo.setPerms(perms);
        return BaseRespVo.ok(adminInfo);
    }

    @RequestMapping("logout")
    public BaseRespVo logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseRespVo.ok();
    }

}
