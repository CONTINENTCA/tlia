package com.zly.tliaswebmanagement.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Component
public class AliyunOSSOperator {

    // 这些参数容易变动 不建议写死 建议写在yml文件
    /*
    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    private String bucketName = "java-ai-continentca";
    private String region = "cn-beijing";
    */
    //使用@Value注解从 yml 自动读取！
    //但是使用value注解太繁琐 不利于服用 我们可以通过@ConfigurationProperties注解封装到一个类中
    /*
    @Value("${aliyun.myoss.myendpoint}")
    private String endpoint;
    @Value("${aliyun.myoss.mybucketname}")
    private String bucketName;
    @Value("${aliyun.myoss.myregion}")
    private String region;
     */
    // 引入类
    @Autowired
    AliyunOSSProperties aliyunOSSProperties;

    public String upload(MultipartFile file) throws Exception {

        // ========= 在这里读取配置 =========
        String endpoint = aliyunOSSProperties.getMyendpoint();
        String bucketName = aliyunOSSProperties.getMybucketname();
        String region = aliyunOSSProperties.getMyregion();

        // 1. 凭证（和你 Demo 一模一样）从环境中获取key
        EnvironmentVariableCredentialsProvider credentialsProvider =
                CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 2. 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + ext;
        String objectName = dir + "/" + newFileName; // dir相当于一个目录

        // 3. 创建 OSS 客户端（完全和你 Demo 一样）
        ClientBuilderConfiguration config = new ClientBuilderConfiguration();
        config.setSignatureVersion(SignVersion.V4);

        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(config)
                .region(region)
                .build();

        try {
            // ==========================================
            // 核心：不存本地！直接用文件流上传！
            // ==========================================
            PutObjectRequest request = new PutObjectRequest(bucketName, objectName,
                    file.getInputStream()  // 直接流上传，不写本地文件
            );

            // 上传文件
            PutObjectResult result = ossClient.putObject(request);
        } finally {
            ossClient.shutdown();
        }

        // 返回可以直接访问的图片URL
        // endpoint.substring(8)截取掉https://
        return "https://" + bucketName + "." + endpoint.substring(8) + "/" + objectName;
        // 返回的 URL 格式（正确示例）
        //https://java-ai-continentca.oss-cn-beijing.aliyuncs.com/2026/03/xxxxxxx.jpg
    }
}