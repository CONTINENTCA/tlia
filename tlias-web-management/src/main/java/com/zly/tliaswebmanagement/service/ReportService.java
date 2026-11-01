package com.zly.tliaswebmanagement.service;

import com.zly.tliaswebmanagement.pojo.ClassOption;
import com.zly.tliaswebmanagement.pojo.DegreeCount;
import com.zly.tliaswebmanagement.pojo.GenderCount;
import com.zly.tliaswebmanagement.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    public abstract JobOption getEmpJobData();

    public abstract JobOption getEmpJobData_no_fucking_map();

    public abstract List<Map<String, Object>> getEmpGenderData();

    public abstract List<GenderCount> getEmpGenderData_no_fucking_map();

    public abstract List<DegreeCount> getStudentDegreeData();

    public abstract ClassOption getStudentClassCountData();
}
