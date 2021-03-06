package com.groupten.project2.mapper;

import com.groupten.project2.bean.Admin;
import com.groupten.project2.bean.AdminExample;
import java.util.List;

import com.groupten.project2.bean.vo.AdminInfoVo;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    long countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    int lastInsertId();

    List<String> selectRoles();

    List<String> selectPasswordByUsername(@Param("username") String username);

    AdminInfoVo selectRolesByUsername(@Param("username") String username);

    AdminInfoVo selectAdminByUsername(@Param("username") String username);

    void updateAdminByInfo(@Param("info") AdminInfoVo infoVo);
}