package com.zly.tliaswebmanagement.service.impl;

import com.zly.tliaswebmanagement.pojo.EmpLog;
import com.zly.tliaswebmanagement.pojo.EmpLog;
import com.zly.tliaswebmanagement.service.EmpLogService;
import com.zly.tliaswebmanagement.mapper.EmpLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpLogServiceImpl implements EmpLogService {

    @Autowired
    private EmpLogMapper empLogMapper;

    /*
    第一类：支持当前事务（有就用，没有就处理）
    REQUIRED（默认值）
    规则：如果当前有事务，就加入；如果没有，就新建一个
    最常用，增删改方法默认就是它
    特点：所有方法在同一个事务里，一个失败全部回滚
    SUPPORTS
    规则：有事务就加入，没有就以非事务方式运行
    适用：查询方法，不强制开启事务
    MANDATORY
    规则：强制必须有事务，没有就直接抛异常
    适用：必须被其他事务方法调用，不能单独执行
    第二类：不支持当前事务（独立运行）
    REQUIRES_NEW（你代码用的）
    规则：不管当前有没有事务，都新建一个独立的新事务
    特点：
    ✅ 新事务完全独立，和原事务隔离
    ✅ 原事务回滚，不影响新事务提交
    ✅ 新事务回滚，也不影响原事务
    适用：日志、消息发送、审计记录（必须成功保存，不因为主业务失败而丢失）
    NOT_SUPPORTED
    规则：始终以非事务方式运行，如果当前有事务，会把事务挂起
    适用：不希望被事务影响的查询 / 操作
    NEVER
    规则：强制非事务，如果当前有事务，直接抛异常
    适用：绝对不能在事务中运行的方法
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW) //如果 public void save(Emp emp)代码回滚了 这个记录日志的方法不会回滚
    @Override
    public void insertLog(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }
}
