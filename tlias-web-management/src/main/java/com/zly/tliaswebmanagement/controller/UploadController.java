package com.zly.tliaswebmanagement.controller;

import com.zly.tliaswebmanagement.pojo.Result;
import com.zly.tliaswebmanagement.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    /**
     * 本地磁盘存储 不推荐
     */
    /*
    @PostMapping("/upload") // 这里的file名称必须和前端一样才能接收到
    public Result upload(@RequestParam("name") String name,
                         @RequestParam("age") Integer age,
                         @RequestParam("file") MultipartFile file) throws IOException {
        log.info("name:{},age:{},file:{}", name, age, file);

        // 获取原始文件名字
        String fileName = file.getOriginalFilename();
        // 使用uuid生成随机名字
        // 如果fileName不是null 那么获取后缀名 如果是null 那么不执行substring操作 直接返回null
        String extension = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : null; //获取后缀名
        String uuid = UUID.randomUUID().toString();
        // 保存图片到本地 默认图片的最大大小是1mb 如果想超过1mb 在yml文件配置
        file.transferTo(new File("D:/Programing/JavaWeb/实战项目/tlias-web-management/MyImage/" + uuid + extension));

        return Result.success();
    }
     */

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) throws Exception {
        String url = aliyunOSSOperator.upload(file);
        return Result.success(url);
    }

}
