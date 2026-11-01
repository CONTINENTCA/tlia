package com.zly.tliaswebmanagement.service.impl;

import com.zly.tliaswebmanagement.mapper.EmpMapper;
import com.zly.tliaswebmanagement.mapper.StudentMapper;
import com.zly.tliaswebmanagement.pojo.*;
import com.zly.tliaswebmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        // 1 调用mapper接口 获取统计数据
        List<Map<String,Object>> list = empMapper.countEmpJobData();
        // 2 组装结果 返回
        List<String> jobList = new ArrayList<>();
        List<Long> dataList = new ArrayList<>();
        // 3. 遍历封装（类型严格对应）
        for (Map<String, Object> map : list) {
            String pos = (String) map.get("pos");      // 职位：字符串
            Long num = (Long) map.get("num");   // 人数：数字
            jobList.add(pos);
            dataList.add(num);
        }
        // 4 封装返回
        return new JobOption(jobList, dataList);
    }

    @Override
    public JobOption getEmpJobData_no_fucking_map() {
        // 1. 直接返回 List<JobCount>，不用强转，不用Map！
        List<JobCount> list = empMapper.countEmpJobData_no_fucking_map();
        List<String> jobList = new ArrayList<>();
        List<Long> dataList = new ArrayList<>();

        // 2. 遍历对象，get方法直接取值，超级清晰！
        for (JobCount count : list) {
            jobList.add(count.getPos());   // 直接get职位
            dataList.add(count.getNum());  // 直接get人数
        }

        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public List<GenderCount> getEmpGenderData_no_fucking_map() {
        return empMapper.countEmpGenderData_no_fucking_map();
    }

    @Override
    public List<DegreeCount> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }

    @Override
    public ClassOption getStudentClassCountData() {
        List<ClassCount> list = studentMapper.countStudentClassData();
        List<String> clazzList = new ArrayList<>();
        List<Long> dataList = new ArrayList<>();
        for (ClassCount count : list) {
            clazzList.add(count.getClassName());
            dataList.add(count.getData());
        }

        return new ClassOption(clazzList, dataList);
    }


}
