package com.experiment.controller;

import cn.hutool.core.util.IdUtil;
import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.User;
import com.experiment.service.CourseService;
import com.experiment.service.MessageService;
import com.experiment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
        return courseService.addressFindCourse(roomId);
    }

    //未实现
    //TODO 数据库表项更改申请中，此处不动
    @ApiOperation("增加课程")
    @PostMapping("/addCourse")
    public Result<?> addCourse(@RequestParam("file")MultipartFile file, @RequestParam("courseName") String courseName,
                               @RequestParam("courseContent") String courseContent, @RequestParam("courseStartWeek") Integer courseStartWeek,
                               @RequestParam("courseEndWeek") Integer courseEndWeek, @RequestParam("courseTime") List<Integer> courseTime, @RequestParam("courseDay") List<Integer> courseDay,
                               @RequestParam("estimatedNumber") Integer estimatedNumber, @RequestParam("roomId") List<String> roomId,
                               @RequestParam("isShared") Boolean isShareable, @RequestParam("isOpening") Boolean isOpening){
        if(getUser().getUserType() == "STUDENT")
            return Result.error("-1", "无权限");
        Course course = new Course(IdUtil.fastSimpleUUID(), courseName, courseContent, getUserId(), new Date(), isOpening);
        courseService.addCourse(course);
        //TODO 时间转换，传入StartWeek，EndWeek，courseDay，返回日期列表
        List<Date> targetDate = null;
        Result<?> excelResult = userService.readUsersByExcel(file);
        if(excelResult.getCode().equals("-1"))
            return excelResult;
        List<User> students = null;
        if(students.size() != estimatedNumber)
            return Result.error("-1", "您输入的学生数量与表格文件中的学生数量不相同");
        students = (List<User>) userService.readUsersByExcel(file).getData();
        return null;
    }

    @ApiOperation("删除课程")
    @PostMapping("/deleteCourse")
    public Result<?> deleteCourse(String courseId){
        return courseService.deleteCourse(courseId);
    }

}
