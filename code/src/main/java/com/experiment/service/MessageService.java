package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.User;

public interface MessageService {
    Result<?> getMessageNum(String userId);
}
