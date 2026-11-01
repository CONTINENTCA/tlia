package com.zly.tliaswebmanagement.controller;


import com.zly.tliaswebmanagement.anno.LogOperation;
import com.zly.tliaswebmanagement.pojo.Emp;
import com.zly.tliaswebmanagement.pojo.EmpQueryParam;
import com.zly.tliaswebmanagement.pojo.PageResult;
import com.zly.tliaswebmanagement.pojo.Result;
import com.zly.tliaswebmanagement.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/*
员工管理Controller
 */
@Slf4j
//RequestMapping("/emps") 这样就不需要在这个类中的XXXMapping方法中写值了
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    // 分页查询
    // 参数通过url ？的方式传递
    // apiFox:http://localhost:8080/emps?page=1&pageSize=5
    /*
    // 传统分页查询
    @GetMapping("/emps")
    // RequestParam还可以自定义默认值
    public Result page(@RequestParam(value = "page",defaultValue = "1") Integer page,
                       @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        log.info("分页查询:{},{}",page,pageSize);
        PageResult<Emp> result = empService.page(page,pageSize);
        return Result.success(result);
    }
     */

    // 使用pageHelper来开发分页查询 推荐
    // PageHelper是第三方提供在Mybatis框架中用来实现分页的插件 用来简化分页操作
    // 1 引入PageHelper依赖
    // 2 定义Mapper接口的查询方法（无需考虑分页）
    // 3 在Service方法中实现分页查询
    @GetMapping("/emps") //http://localhost:8080/emps?page=1&pageSize=5&name=阮&gender=1&begin=2010-01-01&end=2020-01-01
    public Result Page(EmpQueryParam empQueryParam) {
        log.info("分页查询:{}", empQueryParam);
        PageResult<Emp> pageResult = empService.page_use_pagehelper(empQueryParam);
        return Result.success(pageResult);
    }

    // 新增员工
    @LogOperation
    @PostMapping("/emps")
    public Result save(@RequestBody Emp emp){
        log.info("新增员工:{}", emp);
        empService.save(emp);
        return Result.success();
    }

    // 批量删除员工
    @LogOperation
    @DeleteMapping("/emps")
    public Result remove(@RequestParam("ids") List<Integer> ids){
        log.info("删除员工:{}", ids);
        empService.remove(ids);
        return Result.success();
    }

    // 修改员工的查询回显
    @GetMapping("/emps/{id}")
    public Result getInfo(@PathVariable("id") Integer id){
        log.info("修改回显:{}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    // 修改员工信息
    @LogOperation
    @PutMapping("/emps")
    public Result update(@RequestBody Emp emp){
        log.info("修改员工:{}", emp);
        empService.update(emp);
        return Result.success();
    }


}
