package com.groupten.project2.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.groupten.project2.bean.Log;
import com.groupten.project2.bean.LogExample;
import com.groupten.project2.bean.vo.BaseListData;
import com.groupten.project2.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService{
    @Autowired
    LogMapper logMapper;

    @Override
    public BaseListData list(Integer page, Integer limit, String sort, String order, String name) {
        PageHelper.startPage(page,limit);
        LogExample logExample = new LogExample();
        LogExample.Criteria criteria = logExample.createCriteria();
        if((name != null) && (!name.isEmpty())){
            criteria.andAdminEqualTo(name);
        }
        logExample.setOrderByClause(sort + " " +order);
        criteria.andDeletedEqualTo(false);
        List<Log> logs = logMapper.selectByExample(logExample);
        PageInfo<Log> logPageInfo = new PageInfo<>(logs);
        return BaseListData.ok(logs,logPageInfo.getTotal());
    }
}
