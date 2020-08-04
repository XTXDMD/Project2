package com.groupten.project2.service;

import com.groupten.project2.bean.vo.BaseListData;

public interface LogService {
    BaseListData list(Integer page, Integer limit, String sort, String order, String name);
}
