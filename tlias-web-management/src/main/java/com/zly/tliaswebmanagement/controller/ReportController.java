package com.zly.tliaswebmanagement.controller;

import com.zly.tliaswebmanagement.pojo.*;
import com.zly.tliaswebmanagement.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
//@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // 统计员工职位人数
    @GetMapping("/report/empJobData")
    public Result getEmpJobData(){
        log.info("统计员工职位人数");
        //JobOption jobOption = reportService.getEmpJobData();
        JobOption jobOption = reportService.getEmpJobData_no_fucking_map(); // 不他妈用map
        return Result.success(jobOption);
    }

    // 统计员工性别
    /*
    返回数据:
    "data"[
        {"name":"男性员工","value":5},
        {"name":"女性员工","value":6}
        ]
     */
    @GetMapping("/report/empGenderData")
    public Result getEmpGender(){
        log.info("统计员工性别");
        /*
        List<Map<String,Object>> list = reportService.getEmpGenderData();
         */
        // 不使用map集合 封装到对象
        List<GenderCount> list = reportService.getEmpGenderData_no_fucking_map();
        return Result.success(list);

    }

    @GetMapping("/report/studentDegreeData")
    public Result getStudentDegree(){
        log.info("学员学位统计");
        List<DegreeCount> list = reportService.getStudentDegreeData();
        return Result.success(list);
    }

    @GetMapping("/report/studentCountData")
    public Result getStudentClassCount(){
        log.info("班级人数统计");
        ClassOption option = reportService.getStudentClassCountData();
        return Result.success(option);
    }

}
