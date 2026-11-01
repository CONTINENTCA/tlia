package com.zly.tliaswebmanagement.service;

import com.zly.tliaswebmanagement.pojo.Emp;
import com.zly.tliaswebmanagement.pojo.EmpQueryParam;
import com.zly.tliaswebmanagement.pojo.LoginInfo;
import com.zly.tliaswebmanagement.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;


//@Param page 页码
 //@Param pageSize 每页记录数

public interface EmpService {
    /*
    // 传统分页查询
    public abstract PageResult<Emp> page(Integer page, Integer pageSize);
     */

    // 使用pageHelper
    public abstract PageResult<Emp> page_use_pagehelper(EmpQueryParam param);

    public abstract void save(Emp param);

    public abstract void remove(List<Integer> ids);

    public abstract Emp getInfo(Integer id);

    public abstract void update(Emp emp);

    public abstract LoginInfo login(Emp emp);
}
