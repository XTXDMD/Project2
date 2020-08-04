package com.groupten.project2.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.groupten.project2.bean.Admin;
import com.groupten.project2.bean.vo.AdminInfoVo;
import com.groupten.project2.bean.vo.BaseListData;
import com.groupten.project2.bean.vo.BaseRespVo;
import com.groupten.project2.service.AdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;


@RestController
@RequestMapping("admin/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    /**
     * 获取管理员列表
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return adminList
     */
    @RequestMapping("list")
    @RequiresPermissions(value = {"admin:admin:list"})
    public BaseRespVo list(Integer page, Integer limit, String sort, String order,String username) throws JsonProcessingException {
        BaseListData data = adminService.list(page,limit,sort,order,username);
        return BaseRespVo.ok(data);
    }

    /**
     * 新增管理员
     * @param map
     * @return
     */
    @RequestMapping("create")
    @RequiresPermissions(value = {"admin:admin:create"})
    public BaseRespVo create(@RequestBody Map<String,Object> map) throws JsonProcessingException {
        boolean isExist =  adminService.checkUsername((String)map.get("username"));
        if(isExist){
            return BaseRespVo.fail(602,"管理员已存在");
        }
        Admin admin = new Admin();
        admin.setUsername((String)map.get("username"));
        admin.setPassword((String)map.get("password"));
        admin.setAvatar((String)map.get("avatar"));
        admin.setRoleIds(map.get("roleIds").toString());
        adminService.create(admin);
        int lastInsertId = adminService.lastInsertAdmin();
        AdminInfoVo infoVo = new AdminInfoVo();
        infoVo.setUsername((String)map.get("username"));
        infoVo.setPassword((String)map.get("password"));
        infoVo.setAvatar((String)map.get("avatar"));
        Integer[] roles = new ObjectMapper().readValue(map.get("roleIds").toString(),Integer[].class);
        infoVo.setRoleIds(roles);
        infoVo.setId(lastInsertId);
        return BaseRespVo.ok(infoVo);
    }

    @RequestMapping("delete")
    @RequiresPermissions(value = {"admin:admin:delete"})
    public BaseRespVo delete(@RequestBody Map map){
        adminService.delete(map.get("id"));
        return BaseRespVo.ok();
    }

    @RequestMapping("update")
    @RequiresPermissions(value = {"admin:admin:update"})
    public BaseRespVo update(@RequestBody AdminInfoVo infoVo){
        adminService.update(infoVo);
        return BaseRespVo.ok(infoVo);
    }
}
