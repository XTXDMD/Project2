package com.groupten.project2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.groupten.project2.bean.Ad;
import com.groupten.project2.bean.Admin;
import com.groupten.project2.bean.AdminExample;
import com.groupten.project2.bean.vo.AdminInfoVo;
import com.groupten.project2.bean.vo.BaseListData;
import com.groupten.project2.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;


    @Override
    public BaseListData list(Integer page, Integer limit, String sort, String order, String username) throws JsonProcessingException {
        PageHelper.startPage(page, limit);
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        if((username != null) && (!username.isEmpty())){
            criteria.andUsernameEqualTo(username);
        }
        adminExample.setOrderByClause(sort + " " +order);
        criteria.andDeletedEqualTo(false);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        List<AdminInfoVo> infoVos = new ArrayList<>();
        for (Admin admin : admins) {
            String roles = admin.getRoleIds();
                Integer[] RoleList = new ObjectMapper().readValue(roles, Integer[].class);
            infoVos.add(new AdminInfoVo(admin.getId(),admin.getUsername(),admin.getPassword(),admin.getLastLoginIp(),admin.getLastLoginTime(),admin.getAvatar(),admin.getAddTime(),admin.getUpdateTime(),admin.getDeleted(),RoleList));
        }
        PageInfo<Admin> adminPageInfo = new PageInfo<>(admins);
        return BaseListData.ok(infoVos,adminPageInfo.getTotal());
    }

    @Override
    public boolean checkUsername(String username) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<Admin> admin = adminMapper.selectByExample(adminExample);
        if(admin.size() == 0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void create(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public Integer lastInsertAdmin() {
        return adminMapper.lastInsertId();
    }

    @Override
    public void delete(Object id) {
        int idToDelete = (int) id;
        Admin admin = adminMapper.selectByPrimaryKey(idToDelete);
        admin.setDeleted(true);
        adminMapper.updateByPrimaryKey(admin);
    }

    @Override
    public void update(AdminInfoVo infoVo) {
        /*Admin admin = new Admin();
        admin.setId(infoVo.getId());
        admin.setUsername(infoVo.getUsername());
        admin.setPassword(infoVo.getPassword());
        admin.setAvatar(infoVo.getAvatar());
        admin.setRoleIds(infoVo.getRoleIds().toString());
        admin.setUpdateTime(infoVo.getUpdateTime());
        admin.setAddTime(infoVo.getAddTime());*/
        adminMapper.updateAdminByInfo(infoVo);
    }




}
