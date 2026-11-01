package com.zly.tliaswebmanagement.service.impl;

import com.zly.tliaswebmanagement.mapper.DeptMapper;
import com.zly.tliaswebmanagement.pojo.Dept;
import com.zly.tliaswebmanagement.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(int id) {
        deptMapper.deleteById(id);
    }

    @Override
    public void add(Dept dept) {
        // 1 补全基础属性 - createTime updateTime
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        // 2 调用deptMapper中方法
        deptMapper.insert(dept);
    }

    @Override
    public Dept getInfo(Integer deptId) {
        return deptMapper.getInfo(deptId);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }


}
