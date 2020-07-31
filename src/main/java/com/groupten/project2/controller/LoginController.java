package com.groupten.project2.controller;


import com.groupten.project2.bean.AdminInfo;
import com.groupten.project2.bean.vo.BaseRespVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/auth")
public class LoginController {

    @RequestMapping("login")
    public BaseRespVo login(@RequestBody Map map){
        return BaseRespVo.ok("admin");
    }

    @RequestMapping("info")
    public BaseRespVo info(String token){
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAvatar("");
        adminInfo.setName("admin");
        List<String> roles = new ArrayList<>();
        List<String> perms = new ArrayList<>();
        roles.add("超级管理员");
        perms.add("*");
        adminInfo.setRoles(roles);
        adminInfo.setPerms(perms);
        return BaseRespVo.ok(adminInfo);
    }

}
