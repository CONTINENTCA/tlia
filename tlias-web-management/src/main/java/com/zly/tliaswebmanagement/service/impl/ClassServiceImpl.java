package com.zly.tliaswebmanagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zly.tliaswebmanagement.mapper.ClassMapper;
import com.zly.tliaswebmanagement.pojo.*;
import com.zly.tliaswebmanagement.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public PageResult<MyClass> page(ClassQueryParam classQueryParam) {
        PageHelper.startPage(classQueryParam.getPage(), classQueryParam.getPageSize());
        List<MyClass> classList = classMapper.page(classQueryParam);
        PageInfo<MyClass> pageInfo = new PageInfo<>(classList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void deleteById(Integer id) {
        classMapper.deleteById(id);
    }

    @Override
    public void addClass(MyClass myClass) {
        classMapper.addClass(myClass);
    }

    @Override
    public MyClass FindById(Integer id) {
        return classMapper.FindById(id);
    }

    @Override
    public void update(MyClass myClass) {
        classMapper.update(myClass);
    }

    @Override
    public List<MyClass> findAll() {
        return classMapper.findAll();
    }


}
