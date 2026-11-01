package com.zly.tliaswebmanagement.mapper;

import com.zly.tliaswebmanagement.pojo.OperateLog;
import com.zly.tliaswebmanagement.pojo.PageResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    //插入日志数据
    @Insert("insert into tlias.operate_log (operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{l.operateEmpId}, #{l.operateTime}, #{l.className}, #{l.methodName}, #{l.methodParams}, #{l.returnValue}, #{l.costTime});")
    public void insert(@Param("l") OperateLog log);

    public List<OperateLog> page(Integer page, Integer pageSize);
}
