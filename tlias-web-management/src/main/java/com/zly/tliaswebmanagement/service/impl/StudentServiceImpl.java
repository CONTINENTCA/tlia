package com.zly.tliaswebmanagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zly.tliaswebmanagement.mapper.StudentMapper;
import com.zly.tliaswebmanagement.pojo.*;
import com.zly.tliaswebmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentPageResult<Student> page(StudentQueryParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Student> list = studentMapper.page(param);
        PageInfo<Student> pageInfo = new PageInfo<>(list);
        return new StudentPageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        studentMapper.deleteByIds(ids);
    }

    @Override
    public void insert(Student stu) {
        studentMapper.insert(stu);
    }

    @Override
    public Student getById(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    public void update(Student stu) {
        stu.setUpdateTime(LocalDateTime.now());
        studentMapper.update(stu);
    }


    @Override
    public void violate(Integer id, Integer score) {
        Student stu = studentMapper.getById(id);
        stu.setViolationCount(stu.getViolationCount() + 1);
        stu.setViolationScore(stu.getViolationScore() + score);
        studentMapper.update(stu);
    }




}
