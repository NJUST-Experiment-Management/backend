package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.Message;

import java.util.List;

public interface MessageService {
    Result<?> getMessageNum(String userId);

    /**
     * <p>向list里的所有用户发送信息</p>
     * @param userList 用户编号列表
     * @return 成功与否
     */
    Result<?> sendMessage(List<String> userList, Message message);

    /**
     * <p>按照课程编号发送信息</p>
     * @param course 目标课程
     * @return 成功与否
     */
    Result<?> sendMessage(Course course,Message message);
    Result<?> getMessages(String userId);
}
