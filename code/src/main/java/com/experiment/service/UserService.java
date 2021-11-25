package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.User;

public interface UserService {
    Result<?> login(User user);

}
