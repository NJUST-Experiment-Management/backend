package com.experiment.controller;

import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.Message;
import com.experiment.entity.User;
import com.experiment.service.CourseService;
import com.experiment.service.MessageService;
import com.experiment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "用户基本操作")
@RestController
public class UserController extends BaseController{
    @Resource
    UserService userService;
    @Resource
    CourseService courseService;
    @Resource
    MessageService messageService;

    @ApiOperation("用户登录")
    @PostMapping("/login")//ok
    public Result<?> login(@RequestBody User user){
        return userService.login(user);
    }

    @ApiOperation("获取未读消息数量")
    @GetMapping("/getMessageNum")//ok
    public Result<?> getMessageNum(){
        return messageService.getMessageNum(getUserId());
    }

    @ApiOperation("获取用户全部消息")
    @GetMapping("/getMessages")//ok
    public Result<?> getMessages(){ return  messageService.getMessages(getUserId());}

    @ApiOperation("已读消息")
    @GetMapping("/readMessage")
    public Result<?> readMessage(@RequestParam String messageId){ return  messageService.ReadMessage(messageId);}

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

    @ApiOperation("向某身份群体发送消息")
    @PostMapping("/message/sendUserType")
    public Result<?> sendMessageByType(@RequestBody Message message, @RequestParam String userType){
        if(!getUser().getUserType().equals("ADMIN"))
            return Result.error("-1", "无权限");
        Object data = userService.getUserByType(userType).getData();
        if(data == null)
            return Result.error("-1", "系统错误，刷新重试");
        List<String> stringList = new ArrayList<>();
        for(User user : (List<User>) data){
            stringList.add(user.getUserId());
        }

        return messageService.sendMessage(stringList, message);
    }

    @ApiOperation("向班群体发送信息")
    @PostMapping("/message/sendCourse")
    public Result<?> sendMessageByCourse(@RequestBody Message message, @RequestBody String courseId){
        if(!getUser().getUserType().equals("ADMIN"))
            return Result.error("-1", "无权限");
        Object data = courseService.getCourseById(courseId).getData();
        if(data instanceof Course)
            return messageService.sendMessage((Course) data, message);
        return Result.error("-1","不存在此课程");
    }

    @ApiOperation("向多位用户发送信息")
    @PostMapping("/message/sendUsers")
    public Result<?> sendMessage(@RequestBody Message message, @RequestBody List<String> userIds){
        if(!getUser().getUserType().equals("ADMIN"))
            return Result.error("-1", "无权限");
        return messageService.sendMessage(userIds, message);
    }

    @ApiOperation("设置管理员")
    @GetMapping ("/setAdmin")//ok
    public Result<?> setAdmin(@RequestParam String userId){
        if(!getUser().getUserType().equals("ADMIN"))
            return Result.error("-1", "无权限");
        User user = (User) userService.getUserById(userId).getData();
        user.setUserType("ADMIN");
        return userService.updateUserById(user);
    }

    @ApiOperation("查询所有人员")
    @GetMapping("/getAllUsers")
    public Result<?> getAllUsers(){
        if(!getUser().getUserType().equals("ADMIN"))
            return Result.error("-1", "无权限");
        Map<String, List<User>> userMap = new HashMap<>();
        String[] types = new String[]{"STUDENT", "TEACHER", "ADMIN"};
        for(int i = 0 ; i < 3; i++){
            userMap.put(types[i], (List<User>) userService.getUserByType(types[i]).getData());
        }
        return Result.success(userMap);
    }
}
