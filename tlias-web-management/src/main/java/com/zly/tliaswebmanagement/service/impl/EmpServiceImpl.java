package com.zly.tliaswebmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zly.tliaswebmanagement.mapper.EmpExprMapper;
import com.zly.tliaswebmanagement.mapper.EmpMapper;
import com.zly.tliaswebmanagement.pojo.*;
import com.zly.tliaswebmanagement.service.EmpLogService;
import com.zly.tliaswebmanagement.service.EmpService;
import com.zly.tliaswebmanagement.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;
    /*
    // 传统分页查询
    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        // 调用Mapper接口 查询总记录数
        Long total = empMapper.count();
        // 调用Mapper接口 查询结果列表
        List<Emp> rows = empMapper.list((page - 1) * pageSize, pageSize);
        // 封装结果
        return new PageResult<Emp>(total,rows);
    }
     */

    // 使用pageHelper
    @Override
    public PageResult<Emp> page_use_pagehelper(EmpQueryParam empQueryParam) {
        // 设置PageHelper分页参数
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
        // 调用Mapper
        //List<Emp> empList =empMapper.list_use_pageHelper(empQueryParam);
        List<Emp> empList = empMapper.list_use_pageHelper_dynamic(empQueryParam); // 使用动态sql

        // 解析查询结果 封装
        //Page<Emp> p = (Page<Emp>) empList;
        // 3. 用 PageInfo 包装（安全、标准、无强转）
        PageInfo<Emp> pageInfo = new PageInfo<>(empList);

        // 4. 封装返回
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        //return new PageResult<Emp>(p.getTotal(), p.getResult());
    }



    /*
    注解：@Transactional
    作用：将当前方法交给spring进行事务管理 方法执行前 开启事务 成功执行完毕 提交事务 出现异常那么rollback
    位置：业务（service）层的方法上 类上 接口上
    方法上：只给这个方法开启事务

    类上：给类中所有 public 方法自动加上事务
    相当于给每个 public 方法都加了 @Transactional

    接口上：事务注解继承到所有实现类
    实现类的方法自动拥有事务

    rollbackFor是指定发生什么异常 才会回滚
    Exception.class是发生任意异常就会回滚
     */
    @Transactional(rollbackFor = {Exception.class}) // 默认出现RuntimeException才会回滚
    @Override
    public void save(Emp emp) {
        try {
            // 保存员工基本信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());

            if (emp.getPassword() == null) { // 我们创建emp表的时候 password属性是非null 必须给他个默认值 否则报错
                emp.setPassword("123456");
            }

            empMapper.insert(emp);

            // 保存员工工作经历
            List<EmpExpr> exprList = emp.getExprList();
            // 如果不为空
            if (!CollectionUtils.isEmpty(exprList)) {
                // Batch后缀代表批量处理
                // 遍历集合 为empId赋值
                for (EmpExpr expr : exprList) {
                    expr.setEmpId(emp.getId());
                }
                empExprMapper.insertBatch(exprList);
            }
        }finally {
            // 记录操作日志 如果当前事物回滚了 empLogService.insertLog(empLog); 不受影响
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工:" + emp);
            empLogService.insertLog(empLog);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void remove(List<Integer> ids) {
        try{
            // 先删除员工基本信息
            empMapper.delete(ids);

            // 删除员工工作经历
            empExprMapper.deleteBatch(ids);
        }finally {
            // 写入日志
            empLogService.insertLog(new EmpLog(null, LocalDateTime.now(), "删除员工:" + ids));
        }

    }

    // 修改员工查询回显
    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
        try{
            // 先修改员工的基本信息
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.updateById(emp);

            // 根据员工的id 先删除所有经历
            // 只传单个 ID：Collections.singletonList(emp.getId()) 更专业、更优雅、性能更好 ✅
            empExprMapper.deleteBatch(Collections.singletonList(emp.getId()));
            // 再插入新的 被修改过的经历
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
                for (EmpExpr expr : exprList) {
                    expr.setEmpId(emp.getId()); // 如果新增加经历了 给新的经历的员工empId赋值
                }
                // 插入
                empExprMapper.insertBatch(exprList);
            }
        }finally {
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "修改员工:" + emp);
            empLogService.insertLog(empLog);
        }

    }

    // 登录
    @Override
    public LoginInfo login(Emp emp) {
        // 调用mapper接口 根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);

        // 判断是否存在这个员工 如果存在 组装登陆成功信息
        if(e != null) {
            log.info("登陆成功,员工信息:{}",e);
            // 生成JWT令牌
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());

            String jwt = JwtUtils.generateJwt(claims);

            return new LoginInfo(e.getId(),e.getUsername(),e.getName(),jwt);
        }
        // 不存在返回null
        return null;
    }


}
