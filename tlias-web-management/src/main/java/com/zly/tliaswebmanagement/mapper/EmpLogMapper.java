package com.zly.tliaswebmanagement.mapper;

import com.zly.tliaswebmanagement.pojo.EmpLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmpLogMapper {

    @Insert("insert into emp_log (operate_time, info) values (#{e.operateTime}, #{e.info})")
    public void insert(@Param("e") EmpLog empLog);

}
