package com.zly.tliaswebmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobCount {
    // 对应sql中pos别名
    private String pos;

    // 对应 SQL 里的 num 别名
    private Long num; // 这个num接收的是count(*)返回的结果 是Long 不能是Integer
}
