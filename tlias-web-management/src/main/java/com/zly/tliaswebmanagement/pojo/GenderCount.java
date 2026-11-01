package com.zly.tliaswebmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderCount {
    private String name;    // 男性员工 / 女性员工
    private Long value;     // count(*) 返回 Long
}
