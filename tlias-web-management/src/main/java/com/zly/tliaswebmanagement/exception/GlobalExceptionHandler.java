package com.zly.tliaswebmanagement.exception;
import com.zly.tliaswebmanagement.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 统一捕获、处理项目中所有 Controller 抛出的异常
 */
@RestControllerAdvice // 全局增强Controller，统一捕获异常并返回JSON格式
@Slf4j // 自动注入日志对象log，用于打印错误日志
public class GlobalExceptionHandler {

    /**
     * 捕获所有类型的异常（顶级父类Exception）
     * @param e 捕获到的异常对象
     * @return 统一返回错误信息给前端
     */
    @ExceptionHandler(Exception.class) // 指定要捕获的异常类型：Exception=捕获所有异常
    public Result handleException(Exception e) {
        // 打印异常堆栈信息（方便排查问题）
        log.error("系统异常：", e);

        // 返回统一格式的错误结果
        return Result.error("操作失败：" + e.getMessage());
    }

    // 插入 / 修改数据时，有字段违反了「唯一不能重复」的规则，数据库拒绝执行，抛异常。
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("DuplicateKeyException:", e);
        return Result.error("DuplicateKeyException:" + e.getMessage());
    }
}