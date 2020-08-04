package com.groupten.project2.bean;

import lombok.Data;

import java.util.List;

@Data
public class PermissionDetail {
    String id;
    String label;
    PermissionDetailOperation[] children;
}
