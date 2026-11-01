package com.zly.tliaswebmanagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zly.tliaswebmanagement.mapper.OperateLogMapper;
import com.zly.tliaswebmanagement.pojo.MyClass;
import com.zly.tliaswebmanagement.pojo.OperateLog;
import com.zly.tliaswebmanagement.pojo.PageResult;
import com.zly.tliaswebmanagement.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public PageResult<OperateLog> page(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<OperateLog> logList = operateLogMapper.page(page,pageSize);
        PageInfo<OperateLog> pageInfo = new PageInfo<>(logList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
