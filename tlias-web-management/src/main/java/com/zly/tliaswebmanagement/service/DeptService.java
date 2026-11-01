package com.zly.tliaswebmanagement.service;

import com.zly.tliaswebmanagement.pojo.Dept;

import java.util.List;

public interface DeptService {

    /**
     * 查询所有的部门数据
     */
    public abstract List<Dept> findAll();

    public abstract void deleteById(int id);

    public abstract void add(Dept dept);

    public abstract Dept getInfo(Integer deptId);

    public abstract void update(Dept dept);
}
