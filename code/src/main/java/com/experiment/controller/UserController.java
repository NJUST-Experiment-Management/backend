package com.experiment.controller;

import com.experiment.common.Result;
import com.experiment.entity.Message;
import com.experiment.entity.User;
import com.experiment.service.MessageService;
import com.experiment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@Api(tags = "用户基本操作")
@RestController
public class UserController extends BaseController{
    @Resource
    UserService userService;
    @Resource
    MessageService messageService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        return userService.login(user);
    }

    @ApiOperation("获取未读消息数量")
    @GetMapping("/getMessageNum")
    public Result<?> getMessageNum(){
        return messageService.getMessageNum(getUserId());
    }
}
