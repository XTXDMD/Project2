package com.groupten.project2.service;

import com.groupten.project2.bean.Storage;
import com.groupten.project2.bean.vo.BaseListData;

public interface StorageService {
    void create(Storage storage);

    BaseListData list(Integer page, Integer limit, String sort, String order, String key, String name);

    void update(Storage storageToUpdate);

    void delete(Storage storageToDelete);
}
