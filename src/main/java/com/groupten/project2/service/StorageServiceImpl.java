package com.groupten.project2.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.groupten.project2.bean.Storage;
import com.groupten.project2.bean.StorageExample;
import com.groupten.project2.bean.vo.BaseListData;
import com.groupten.project2.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService{
    @Autowired
    StorageMapper storageMapper;

    @Override
    public void create(Storage storage) {
        storageMapper.insert(storage);
    }

    @Override
    public BaseListData list(Integer page, Integer limit, String sort, String order, String key, String name) {
        PageHelper.startPage(page,limit);
        StorageExample storageExample = new StorageExample();
        StorageExample.Criteria criteria = storageExample.createCriteria();
        if((name != null) && (!name.isEmpty())){
            criteria.andNameEqualTo(name);
        }
        if((key != null) && (!key.isEmpty())){
            criteria.andKeyEqualTo(key);
        }
        storageExample.setOrderByClause(sort + " " +order);
        criteria.andDeletedEqualTo(false);
        List<Storage> storages = storageMapper.selectByExample(storageExample);
        PageInfo<Storage> logPageInfo = new PageInfo<>(storages);
        return BaseListData.ok(storages,logPageInfo.getTotal());
    }

    @Override
    public void update(Storage storageToUpdate) {
        Storage storage = new Storage();
        storage.setId(storageToUpdate.getId());
        storage.setName(storageToUpdate.getName());
        storageMapper.updateByPrimaryKeySelective(storage);
    }

    @Override
    public void delete(Storage storageToDelete) {
        Storage storage = new Storage();
        storage.setId(storageToDelete.getId());
        storage.setDeleted(true);
        storageMapper.updateByPrimaryKeySelective(storage);
    }
}
