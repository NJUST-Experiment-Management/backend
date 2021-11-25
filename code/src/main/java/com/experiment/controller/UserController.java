package com.experiment.controller;

import com.experiment.common.Result;
import com.experiment.entity.User;
import com.experiment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@Api(tags = "用户操作")
@RestController
public class UserController {
    @Resource
    UserService userService;
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        return userService.login(user);
    }
}
