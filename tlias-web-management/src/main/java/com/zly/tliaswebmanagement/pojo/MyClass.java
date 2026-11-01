package com.zly.tliaswebmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyClass {
    private Integer id;
    private String name;
    private String room;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Integer masterId;
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
    private String masterName;
    private String status;

    //学科, 1:java, 2:前端, 3:大数据, 4:Python 5:Go, 6:嵌入式
    private Integer subject;
}
