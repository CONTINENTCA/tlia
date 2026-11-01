package com.zly.tliaswebmanagement.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "aliyun.myoss") //prefix是前缀的意思 类的属性名必须和yml文件中的一致
public class AliyunOSSProperties {
    private String myendpoint;
    private String mybucketname;
    private String myregion;
}
