package com.experiment.controller;

import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.User;
import com.experiment.service.CourseService;
import com.experiment.service.MessageService;
import com.experiment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "用户基本操作")
@RestController
public class CourseController extends BaseController{
    @Resource
    UserService userService;
    @Resource
    MessageService messageService;
    @Resource
    CourseService courseService;

    @ApiOperation("根据学生id查课")
    @PostMapping("/studentIdFindArrCourse")
    public Result<?> studentIdFindCourse(@RequestBody User user){
        return courseService.studentIdFindCourse(user.getUserId());
    }

    @ApiOperation("根据时间查课")
    @PostMapping("/TimeFindCourse")
    public Result<?> TimeFindCourse(User user, Date date1, Date date2){
        return courseService.TimeFindCourse(user,date1,date2);
    }

    @ApiOperation("增加课程")
    @PostMapping("/addCourse")
    public Result<?> addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @ApiOperation("删除课程")
    @PostMapping("/deleteCourse")
    public Result<?> deleteCourse(@RequestBody Course course){
        return courseService.deleteCourse(course);
    }


//    @ApiOperation("获取未读消息数量")
//    @GetMapping("/getMessageNum")
//    public Result<?> getMessageNum(){
//        return messageService.getMessageNum(getUserId());
//    }
}
