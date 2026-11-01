package com.zly.tliaswebmanagement.mapper;

import com.zly.tliaswebmanagement.pojo.ClassCount;
import com.zly.tliaswebmanagement.pojo.DegreeCount;
import com.zly.tliaswebmanagement.pojo.Student;
import com.zly.tliaswebmanagement.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {

    public List<Student> page(@Param("p") StudentQueryParam param);

    public void deleteByIds(@Param("ids") List<Integer> ids);

    public void insert(@Param("stu") Student stu);

    public Student getById(@Param("id") Integer id);

    public void update(@Param("stu") Student stu);

    public List<DegreeCount> countStudentDegreeData();

    public List<ClassCount> countStudentClassData();
}
