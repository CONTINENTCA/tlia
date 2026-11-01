package com.zly.tliaswebmanagement.service;

import com.zly.tliaswebmanagement.pojo.*;

import java.util.List;

public interface ClassService {

    public abstract PageResult<MyClass> page(ClassQueryParam classQueryParam);

    public abstract void deleteById(Integer id);

    public abstract void addClass(MyClass myClass);

    public abstract MyClass FindById(Integer id);

    public abstract void update(MyClass myClass);

    public abstract List<MyClass> findAll();
}
