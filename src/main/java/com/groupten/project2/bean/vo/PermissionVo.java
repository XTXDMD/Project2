package com.groupten.project2.bean.vo;

import lombok.Data;

@Data
public class PermissionVo {
    Integer id;
    Integer roleId;
    String[] permissions;
}
