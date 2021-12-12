package com.experiment.controller;

import com.experiment.common.Result;
import com.experiment.entity.Course;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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

    @ApiOperation("获取用户全部消息")
    @GetMapping("/getMessages")
    public Result<?> getMessages(){ return  messageService.getMessages(getUserId());}

    @ApiOperation("更新用户信息")
    @PostMapping("/updateUser")
    public Result<?> updateUserMsg(@RequestBody User user){
        return userService.updateUserById(user);
    }

    @ApiOperation("导入用户名单")
    @PostMapping("/importUser")
    public Result<?> importUserMsg(@RequestBody MultipartFile file){
        if(!getUser().getUserType().equals("ADMIN"))
            return Result.error("-1", "无权限");
        return userService.importUserByExcel(file);
    }

    @ApiOperation("向班群体发送信息")
    @PostMapping("/message/sendCourse")
    public Result<?> sendMessage(@RequestBody Message message, @RequestBody Course course){
        return messageService.sendMessage(course, message);
    }

    @ApiOperation("向多位用户发送信息")
    @PostMapping("/message/sendUsers")
    public Result<?> sendMessage(@RequestBody Message message, @RequestBody List<String> userIds){
        return messageService.sendMessage(userIds, message);
    }

}
