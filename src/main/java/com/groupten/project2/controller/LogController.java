package com.groupten.project2.controller;


import com.groupten.project2.bean.vo.BaseListData;
import com.groupten.project2.bean.vo.BaseRespVo;
import com.groupten.project2.service.LogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("admin/log")
@RestController
public class LogController {
    @Autowired
    LogService logService;

    /**
     * 获取日志列表
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param name
     * @return BaseResp
     */
    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String sort, String order,String name){
        BaseListData data = logService.list(page,limit,sort,order,name);
        return BaseRespVo.ok(data);
    }
}
