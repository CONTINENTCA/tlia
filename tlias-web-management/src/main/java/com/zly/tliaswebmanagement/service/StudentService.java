package com.zly.tliaswebmanagement.service;

import com.zly.tliaswebmanagement.pojo.Student;
import com.zly.tliaswebmanagement.pojo.StudentPageResult;
import com.zly.tliaswebmanagement.pojo.StudentQueryParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    public abstract StudentPageResult<Student> page(StudentQueryParam param);

    public abstract void deleteByIds(List<Integer> ids);

    public abstract void insert(Student stu);

    public abstract Student getById(Integer id);

    public abstract void update(Student stu);

    public abstract void violate(Integer id, Integer score);
}
