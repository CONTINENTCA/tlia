package com.zly.tliaswebmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Integer id;             // 学员ID
    private String name;            // 姓名
    private String no;              // 学号
    private Integer gender;         // 性别 1:男 2:女
    private String phone;           // 手机号
    private Integer degree;         // 学历 1-初中 2-高中 3-大专 4-本科 5-硕士 6-博士
    private String idCard;          // 身份证号
    private Integer isCollege;      // 是否在校生 0-否 1-是
    private String address;         // 地址
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate graduationDate; // 毕业日期

    private Integer violationCount = 0; // 违纪次数
    private Integer violationScore = 0;// 违纪分数
    private Integer clazzId;       // 班级ID
    private String clazzName;      // 班级名称

    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间
}