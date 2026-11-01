package com.zly.tliaswebmanagement.aop;

import com.zly.tliaswebmanagement.mapper.OperateLogMapper;
import com.zly.tliaswebmanagement.pojo.OperateLog;
import com.zly.tliaswebmanagement.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect // 标识当前是个AOP类
@Component
@Slf4j
public class RecordOperateLog {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.zly.tliaswebmanagement.anno.LogOperation)") // 切入点表达式
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();

        // 执行原始方法
        Object obj =  joinPoint.proceed();

        // 记录方法运行的结束时间 记录耗时
        Long endTime = System.currentTimeMillis();
        Long costTime = endTime - startTime;

        // 构建日志实体
        OperateLog olog = new OperateLog();
        olog.setCostTime(costTime);
        olog.setOperateEmpId(getCurrentUserId());
        olog.setOperateTime(LocalDateTime.now());
        olog.setClassName(joinPoint.getTarget().getClass().getName());
        olog.setMethodName(joinPoint.getSignature().getName());
        olog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        olog.setReturnValue(obj != null ? obj.toString() : "void");


        // 保存日志
        log.info("记录操作日志:{}", olog);
        operateLogMapper.insert(olog);

        return obj;
    }

    private Integer getCurrentUserId(){
        // 实现获取当前登录用户的id
        return CurrentHolder.GetCurrentId();
    }
}
