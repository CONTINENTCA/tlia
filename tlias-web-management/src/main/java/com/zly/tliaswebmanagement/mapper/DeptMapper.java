package com.zly.tliaswebmanagement.mapper;

import com.zly.tliaswebmanagement.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {

    // 第一钟方式
    // 手动结果映射 听过@Results和@Result进行手动结果映射
    // column是表的字段名 property是封装类的属性名
    @Results({
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "update_time",property = "updateTime")
    })
    @Select("select id,name,create_time,update_time from tlias.dept order by update_time desc")
    // 第二种方式 起别名
    //@Select("select id,name,create_time as createTime,update_time as updateTime from tlias.dept order by update_time desc")
    public abstract List<Dept> findAll();

    //  方式三 开启驼峰命名：如果字段名与属性名符合驼峰命名规则mybatis会自动通过驼峰命名规则映射
    // 需要在application.yml中配置


    /**
     * 根据id删除部门
     */
    @Delete("delete from tlias.dept where id = #{id}")
    public abstract void deleteById(@Param("id") int MyId);

    /**
     * 根据请求体的name:""增加部门
     */
    @Insert("insert into tlias.dept(name,create_time,update_time) values(#{dept.name},#{dept.createTime},#{dept.updateTime})")
    public abstract void insert(@Param("dept") Dept dept);

    /**
     * 查询回显
     */
    @Select("select id,name,create_time as createTime,update_time as updateTime from tlias.dept where id = #{id}")
    public abstract Dept getInfo(@Param("id") Integer deptId);

    /**
     * 修改部门
     */
    @Update("update tlias.dept set name = #{dept.name},update_time = #{dept.updateTime} where id = #{dept.id}")
    public abstract void update(@Param("dept") Dept dept);
}
