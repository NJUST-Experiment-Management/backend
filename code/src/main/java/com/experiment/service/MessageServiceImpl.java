package com.experiment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.experiment.common.Result;
import com.experiment.entity.User;
import com.experiment.mapper.MessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageServiceImpl implements MessageService{
    @Resource
    MessageMapper messageMapper;
    @Override
    public Result<?> getMessageNum(String userId) {
        return Result.success(messageMapper.getMessageNum(userId));
    }
}
