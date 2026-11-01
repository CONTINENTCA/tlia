package com.zly.tliaswebmanagement.controller;

import com.zly.tliaswebmanagement.anno.LogOperation;
import com.zly.tliaswebmanagement.pojo.Result;
import com.zly.tliaswebmanagement.pojo.Student;
import com.zly.tliaswebmanagement.pojo.StudentPageResult;
import com.zly.tliaswebmanagement.pojo.StudentQueryParam;
import com.zly.tliaswebmanagement.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public Result page(StudentQueryParam param) {
        log.info("分页查询学生");
        StudentPageResult<Student> result = studentService.page(param);
        return Result.success(result);
    }

    @LogOperation
    @DeleteMapping("/students/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        studentService.deleteByIds(ids);
        return Result.success();
    }

    @LogOperation
    @PostMapping("/students")
    public Result insertStudent(@RequestBody Student stu){
        studentService.insert(stu);
        return Result.success();
    }

    @GetMapping("/students/{id}")
    public Result getById(@PathVariable Integer id){
        Student stu = studentService.getById(id);
        return Result.success(stu);
    }

    @LogOperation
    @PutMapping("/students")
    public Result update(@RequestBody Student stu){
        studentService.update(stu);
        return Result.success();
    }

    @LogOperation
    @PutMapping("/students/violation/{id}/{score}")
    public Result violate(@PathVariable("id") Integer id,
                          @PathVariable("score") Integer score) {
        studentService.violate(id,score);
        return Result.success();
    }
}
