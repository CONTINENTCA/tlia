package com.zly.tliaswebmanagement.controller;

import com.zly.tliaswebmanagement.anno.LogOperation;
import com.zly.tliaswebmanagement.pojo.*;
import com.zly.tliaswebmanagement.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ClassController {
    @Autowired
    private ClassService classService;
    
    @GetMapping("/clazzs")
    public Result ClassPage(ClassQueryParam classQueryParam) {
        log.info("查询所有的班级");
        PageResult<MyClass> classPageResult = classService.page(classQueryParam);
        return Result.success(classPageResult);
    }

    @LogOperation
    @DeleteMapping("/clazzs/{id}")
    public Result deleteClass(@PathVariable Integer id) {
        log.info("根据id删除班级");
        classService.deleteById(id);
        return Result.success();
    }

    @LogOperation
    @PostMapping("/clazzs")
    public Result addClass(@RequestBody MyClass myClass){
        log.info("增加班级");
        classService.addClass(myClass);
        return Result.success();
    }

    @GetMapping("/clazzs/{id}")
    public Result FindById(@PathVariable Integer id){
        log.info("根据id查询:{}",id);
        MyClass myClass = classService.FindById(id);
        return Result.success(myClass);
    }

    @LogOperation
    @PutMapping("/clazzs")
    public Result update(@RequestBody MyClass myClass){
        log.info("修改班级:{}",myClass);
        classService.update(myClass);
        return Result.success();
    }

    @GetMapping("/clazzs/list")
    public Result findAll(){
        log.info("查询所有班级");
        return Result.success(classService.findAll());
    }
}
