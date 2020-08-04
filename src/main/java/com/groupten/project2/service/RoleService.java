package com.groupten.project2.service;

import com.groupten.project2.bean.Role;
import com.groupten.project2.bean.SystemPermission;
import com.groupten.project2.bean.vo.BaseListData;
import com.groupten.project2.bean.vo.PermissionVo;

import java.util.List;

public interface RoleService {
    List<Role> options();

    BaseListData list(Integer page, Integer limit, String sort, String order, String name);

    Role create(Role roleToCreate);

    void update(Role roleToUpdate);

    boolean checkAdminRole(Integer roleIdToDelete);

    void delete(Role roleToDelete);

    PermissionVo getAssignedPermission(Integer roleId);

    List<SystemPermission> getSystemPermission();
}
