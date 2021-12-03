package com.experiment.controller;

import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.User;
import com.experiment.service.CourseService;
import com.experiment.service.MessageService;
import com.experiment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "课程相关")
@RestController("/course")
public class CourseController extends BaseController{
    @Resource
    UserService userService;
    @Resource
    MessageService messageService;
    @Resource
    CourseService courseService;

    @ApiOperation("根据id查课")
    @GetMapping("/IdFindCourse")
    public Result<?> IdFindCourse(@RequestBody User user){
        return courseService.IdFindCourse(user.getUserId());
    }

    @ApiOperation("根据时间查课")
    @GetMapping("/TimeFindCourse")
    public Result<?> TimeFindCourse(User user, Date date1, Date date2,int time1,int time2){
        return courseService.TimeFindCourse(user,date1,date2,time1,time2);
    }

    @ApiOperation("根据地点查课")
    @GetMapping("/AddressFindCourse")
    public Result<?> AddressFindCourse(String roomId){
        return courseService.AddressFindCourse(roomId);
    }

    //未实现
    @ApiOperation("增加课程")
    @PostMapping("/addCourse")
    public Result<?> addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @ApiOperation("删除课程")
    @PostMapping("/deleteCourse")
    public Result<?> deleteCourse(String courseId){
        return courseService.deleteCourse(courseId);
    }

}
