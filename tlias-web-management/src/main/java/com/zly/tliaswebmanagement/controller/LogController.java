package com.zly.tliaswebmanagement.controller;

import com.zly.tliaswebmanagement.mapper.OperateLogMapper;
import com.zly.tliaswebmanagement.pojo.OperateLog;
import com.zly.tliaswebmanagement.pojo.PageResult;
import com.zly.tliaswebmanagement.pojo.Result;
import com.zly.tliaswebmanagement.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/log/page")
    public Result page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        PageResult<OperateLog> pageResult = logService.page(page,pageSize);

        return Result.success(pageResult);
    }
}
