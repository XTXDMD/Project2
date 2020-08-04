package com.groupten.project2.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.groupten.project2.bean.Storage;
import com.groupten.project2.bean.vo.BaseListData;
import com.groupten.project2.bean.vo.BaseRespVo;
import com.groupten.project2.service.StorageService;
import com.groupten.project2.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("admin/storage")

public class StorageController {
    @Value("${base.url}")
    String uploadBaseUrl;

    @Autowired
    StorageService storageService;

    /**
     * 获取对象列表
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String sort, String order,String key,String name) throws JsonProcessingException {
        BaseListData data = storageService.list(page,limit,sort,order,key,name);
        return BaseRespVo.ok(data);
    }


    /**
     * 上传文件
     * @param myfile
     * @return
     * @throws IOException
     */
    @RequestMapping("create")
    public BaseRespVo create(@RequestParam("file") MultipartFile myfile) throws IOException {

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String originalFIlename = myfile.getOriginalFilename();
        originalFIlename = uuid + originalFIlename;

        File file = new File( uploadBaseUrl,originalFIlename);

        myfile.transferTo(file);

        String accessUrl = "http://localhost:8081/pic/  " + originalFIlename;
        Map imgurl = new HashMap();
        imgurl.put("url",accessUrl);

        Storage storage = new Storage();
        storage.setName(myfile.getOriginalFilename());
        storage.setKey(originalFIlename);
        storage.setSize((int) myfile.getSize());
        storage.setType(myfile.getContentType());
        storage.setUrl(accessUrl);
        storageService.create(storage);
        return BaseRespVo.ok(storage);
    }

    /**
     * 更新对象
     * @param storageToUpdate
     * @return
     */
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody Storage storageToUpdate){
        storageService.update(storageToUpdate);
        return BaseRespVo.ok(storageToUpdate);
    }

    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Storage storageToDelete){
        storageService.delete(storageToDelete);
        return BaseRespVo.ok();
    }

}
