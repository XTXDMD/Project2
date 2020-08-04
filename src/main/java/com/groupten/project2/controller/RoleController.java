package com.groupten.project2.controller;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.groupten.project2.bean.Permission;
import com.groupten.project2.bean.Role;
import com.groupten.project2.bean.SystemPermission;
import com.groupten.project2.bean.vo.AdminRoleVo;
import com.groupten.project2.bean.vo.BaseListData;
import com.groupten.project2.bean.vo.BaseRespVo;
import com.groupten.project2.bean.vo.PermissionVo;
import com.groupten.project2.mapper.PermissionMapper;
import com.groupten.project2.service.AdminService;
import com.groupten.project2.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionMapper permissionMapper;
    /**
     * 获取管理员角色定义
     * @return
     */
    @RequestMapping("options")
    public BaseRespVo options(){
        List<Role> roles = roleService.options();
        List<AdminRoleVo> roleVos = new ArrayList<>();
        for (Role role : roles) {
            roleVos.add(new AdminRoleVo(role.getId(),role.getName()));
        }
        return BaseRespVo.ok(roleVos);
    }

    /**
     * 获取角色列表
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param name
     * @return
     */
    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String sort, String order,String name){
        BaseListData data = roleService.list(page,limit,sort,order,name);
        return BaseRespVo.ok(data);
    }

    /**
     * 创建角色
     * @param roleToCreate
     * @return
     */
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody Role roleToCreate){
        Role roleVo = roleService.create(roleToCreate);
        return BaseRespVo.ok(roleVo);
    }

    /**
     * 修改角色
     * @param roleToUpdate
     * @return
     */
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody Role roleToUpdate){
        roleService.update(roleToUpdate);
        return BaseRespVo.ok();
    }

    /**
     * 删除角色
     * @param roleToDelete
     * @return
     */
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Role roleToDelete){
        boolean hasRoleInAdmins = roleService.checkAdminRole(roleToDelete.getId());
        if(hasRoleInAdmins){
            return BaseRespVo.fail(642,"当前角色存在管理员，不能删除");
        }else {
            roleService.delete(roleToDelete);
            return BaseRespVo.ok();
        }
    }

    @RequestMapping(value = "permissions", method = RequestMethod.GET)
    public BaseRespVo permission(@RequestParam("roleId") Integer roleId){
        PermissionVo permissionVo = roleService.getAssignedPermission(roleId);
        List<SystemPermission> systemPermission = roleService.getSystemPermission();
        Map<String,Object> data = new HashMap();
        data.put("assignedPermissions",permissionVo.getPermissions());
        data.put("systemPermissions",systemPermission);
        return BaseRespVo.ok(data);
    }

    @RequestMapping(value = "permissions", method = RequestMethod.POST)
    public BaseRespVo permission(@RequestBody Map<String,Object> permissionToUpdate){
        PermissionVo permissionVo = new PermissionVo();
        permissionVo.setId((Integer) permissionToUpdate.get("roleId"));
        ArrayList permissions = (ArrayList) permissionToUpdate.get("permissions");
        String[] permissions1 = new String[permissions.size()];
        permissionVo.setPermissions((String[]) permissions.toArray(permissions1));
        permissionMapper.updatePermission(permissionVo);
        return BaseRespVo.ok();
    }
}
