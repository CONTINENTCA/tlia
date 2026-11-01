package com.zly.tliaswebmanagement.service;

import com.zly.tliaswebmanagement.pojo.OperateLog;
import com.zly.tliaswebmanagement.pojo.PageResult;

import java.util.List;

public interface LogService {
    public abstract PageResult<OperateLog> page(Integer page, Integer pageSize);
}
