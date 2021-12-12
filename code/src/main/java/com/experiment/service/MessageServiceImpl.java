package com.experiment.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.Message;
import com.experiment.entity.User;
import com.experiment.mapper.CourseMapper;
import com.experiment.mapper.MessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Resource
    MessageMapper messageMapper;
    @Resource
    CourseService courseService;
    @Override
    public Result<?> getMessageNum(String userId) {
        return Result.success(messageMapper.getMessageNum(userId));
    }

    @Override
    public Result<?> sendMessage(List<String> userList, Message message) {
        for(String userId:userList){
            message.setReceiverId(userId);
            message.setMessageId(IdUtil.fastSimpleUUID());
            messageMapper.insert(message);
        }
        return Result.success();
    }

    @Override
    public Result<?> sendMessage(Course course, Message message) {
        List<String> userList= (List<String>) courseService.getStudentList(course).getData();
        String teacherId=course.getTeacherId();
        userList.add(teacherId);
        sendMessage(userList,message);
        return null;
    }
    @Override
    public Result<?> getMessages(String userId) {
        return Result.success(messageMapper.getMessages(userId));
    }
}
