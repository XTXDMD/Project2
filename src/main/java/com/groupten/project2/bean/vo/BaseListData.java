package com.groupten.project2.bean.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseListData<T> {
    List<T> items;
    long total;

    public static <T> BaseListData ok(List<T> items,long total){
        return new BaseListData(items,total);
    }
}
