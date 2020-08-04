package com.groupten.project2.utils;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUploadUtils {
    public static final String BASE_PATH = "/src/main/resources/static";

    public static String upload(MultipartFile imageFile) {

        if (imageFile.isEmpty()) {
            return null;
        }

        String filename = imageFile.getOriginalFilename();

        String ext= null;
        if(filename.contains(".")){
            ext = filename.substring(filename.lastIndexOf("."));
        }else{
            ext = "";
        }

        String uuid =  UUID.randomUUID().toString().replaceAll("-", "");
        String nfileName = uuid + ext;

        File fileSourcePath = new File(BASE_PATH);
        File targetFile = new File(fileSourcePath,nfileName);
        if (!fileSourcePath.exists()) {
            fileSourcePath.mkdirs();
        }
        try {
            imageFile.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String accessUrl =  "/" + nfileName;
        return accessUrl;
    }

}
