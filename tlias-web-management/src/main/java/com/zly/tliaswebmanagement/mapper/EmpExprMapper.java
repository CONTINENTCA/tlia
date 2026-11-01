package com.zly.tliaswebmanagement.mapper;

import com.zly.tliaswebmanagement.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
员工工作经历
 */
@Mapper
public interface EmpExprMapper {
    /**
     * 批量保存员工工作经历
     */
    public void insertBatch(@Param("exprList") List<EmpExpr> exprList);

    /**
     * 批量删除员工工作经历
     */
    public void deleteBatch(@Param("ids") List<Integer> ids);
}
