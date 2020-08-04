package com.groupten.project2.bean;

import lombok.Data;

import java.util.List;

@Data
public class SystemPermission {
    String id;
    String label;
    PermissionDetail[] children;
}
