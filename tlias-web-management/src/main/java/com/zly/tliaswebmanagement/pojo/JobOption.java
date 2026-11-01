package com.zly.tliaswebmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOption {
    private List<String> jobList; // 职位列表
    private List<Long> dataList; // 数据列表 这里接收的是count(*)返回的结果 必须用Long
    /*
    count(*) 返回的是 Long 类型
    Map 强转必须用 Long，不能用 Integer
     */
}
