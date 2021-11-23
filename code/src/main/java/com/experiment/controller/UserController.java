package com.experiment.controller;

import com.experiment.common.Result;
import com.experiment.entity.User;
import com.experiment.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    UserService userService;
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        return userService.login(user);
    }
}
