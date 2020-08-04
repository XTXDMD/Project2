package com.groupten.project2.mapper;

import com.groupten.project2.bean.Permission;
import com.groupten.project2.bean.PermissionDetail;
import com.groupten.project2.bean.PermissionExample;

import java.util.ArrayList;
import java.util.List;

import com.groupten.project2.bean.SystemPermission;
import com.groupten.project2.bean.vo.PermissionVo;
import org.apache.ibatis.annotations.Param;

public interface PermissionMapper {
    long countByExample(PermissionExample example);

    int deleteByExample(PermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    PermissionVo selectPermissionByRoleId(@Param("roleId") Integer roleId);

    Integer updatePermission(@Param("permissionVo") PermissionVo permissionVo);

    List<SystemPermission> selectSystemPermission();

    PermissionDetail selectOperationsByPId(@Param("id") String id);

}