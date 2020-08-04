package com.groupten.project2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.groupten.project2.bean.Admin;
import com.groupten.project2.bean.Role;
import com.groupten.project2.bean.vo.AdminInfoVo;
import com.groupten.project2.bean.vo.BaseListData;

import java.util.List;

public interface AdminService {

    BaseListData list(Integer page, Integer limit, String sort, String order,String username) throws JsonProcessingException;


    void create(Admin admin);

    Integer lastInsertAdmin();

    void delete(Object id);

    void update(AdminInfoVo infoVo);

    boolean checkUsername(String username);
}
