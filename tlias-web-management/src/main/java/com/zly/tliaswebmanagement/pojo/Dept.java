package com.zly.tliaswebmanagement.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/*
实体类属性名和数据库表查询返回的字段名一致，mybatis会自动封装。
如果实体类属性名和数据库表查询返回的字段名不一致，不能自动封装。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
