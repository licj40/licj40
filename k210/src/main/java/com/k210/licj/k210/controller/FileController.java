package com.k210.licj.k210.controller;

import com.k210.licj.k210.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@Slf4j
public class FileController {

    @Value("${filePath}")
    private String dirPath;

    @PostMapping("/upload")
    public JsonResult upLoad(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID()+suffix;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("/yyyy/MM/dd/");
        String datePath = simpleDateFormat.format(new Date());
        File dirFile = new File(dirPath+datePath);
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }
        log.info("文件存储地址={}",datePath+fileName);
        file.transferTo(new File(dirPath+datePath+fileName));
        return JsonResult.ok(datePath+fileName);
    }

    @PostMapping("/remove")
    public JsonResult remove(@RequestBody String url){
        new File(dirPath+url).delete();
        return JsonResult.ok();
    }

}
