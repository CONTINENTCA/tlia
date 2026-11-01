package com.zly.tliaswebmanagement.mapper;

import com.zly.tliaswebmanagement.pojo.ClassQueryParam;
import com.zly.tliaswebmanagement.pojo.MyClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ClassMapper {

    public List<MyClass> page(@Param("p") ClassQueryParam classQueryParam);

    public void deleteById(@Param("id") Integer id);

    public void addClass(@Param("class") MyClass myClass);

    public MyClass FindById(@Param("id") Integer id);

    public void update(@Param("myclass") MyClass myClass);

    public List<MyClass> findAll();
}
