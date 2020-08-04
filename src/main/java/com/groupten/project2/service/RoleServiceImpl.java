package com.groupten.project2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.groupten.project2.bean.PermissionDetail;
import com.groupten.project2.bean.Role;
import com.groupten.project2.bean.RoleExample;
import com.groupten.project2.bean.SystemPermission;
import com.groupten.project2.bean.vo.BaseListData;
import com.groupten.project2.bean.vo.PermissionVo;
import com.groupten.project2.mapper.AdminMapper;
import com.groupten.project2.mapper.PermissionMapper;
import com.groupten.project2.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Role> options() {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Role> roles = roleMapper.selectByExample(roleExample);
        return roles;
    }

    @Override
    public BaseListData list(Integer page, Integer limit, String sort, String order, String name) {
        PageHelper.startPage(page,limit);
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        if((name != null) && (!name.isEmpty())){
            criteria.andNameEqualTo(name);
        }
        roleExample.setOrderByClause(sort + " " +order);
        criteria.andDeletedEqualTo(false);
        List<Role> Roles = roleMapper.selectByExample(roleExample);
        PageInfo<Role> logPageInfo = new PageInfo<>(Roles);
        return BaseListData.ok(Roles,logPageInfo.getTotal());
    }

    @Override
    public Role create(Role roleToCreate) {
        roleMapper.insert(roleToCreate);
        int lastUpdateId = roleMapper.lastInsertId();
        Role roleVo = roleMapper.selectByPrimaryKey(lastUpdateId);
        return roleVo;
    }

    @Override
    public void update(Role roleToUpdate) {
        roleMapper.updateByPrimaryKey(roleToUpdate);
    }


    /**
     * 检查是否存在当前角色管理员
     * @param roleIdToDelete
     * @return
     */
    @Override
    public boolean checkAdminRole(Integer roleIdToDelete) {
        List<String> roles = adminMapper.selectRoles();
        Map<Integer,Integer> roleIds = new HashMap<>();
        for (String role : roles) {
            ArrayList<Integer> roleList = null;
            try {
                //将json字符串转换为ArrayList
                roleList = new ObjectMapper().readValue(role, ArrayList.class);
                for (Integer roleId : roleList) {
                    /*判断角色id是否已在map中存在，
                    * 1.已存在：跳过
                    * 2.不存在：放入
                     */
                    if(roleIds.containsKey(roleId)){
                        continue;
                    }else {
                        roleIds.put(roleId,roleId);
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //判断待删除角色id是否存在于map中
        if(roleIds.containsKey(roleIdToDelete)){
            return true;
        }
        return false;
    }

    @Override
    public void delete(Role roleToDelete) {
        roleToDelete.setDeleted(true);
        roleMapper.updateByPrimaryKey(roleToDelete);
    }

    @Override
    public PermissionVo getAssignedPermission(Integer roleId) {
        PermissionVo permissions = permissionMapper.selectPermissionByRoleId(roleId);
        return permissions;
    }

    @Override
    public List<SystemPermission> getSystemPermission() {
        List<SystemPermission> systemPermission = permissionMapper.selectSystemPermission();
        for (SystemPermission permission : systemPermission) {
            PermissionDetail[] permissionDetails = new PermissionDetail[permission.getChildren().length];
            for (int i = 0; i < permission.getChildren().length; i++) {
                String id = permission.getChildren()[i].getId();
                permissionDetails[i] = permissionMapper.selectOperationsByPId(id);
            }
            permission.setChildren(permissionDetails);
        }
        return systemPermission;
    }


}
