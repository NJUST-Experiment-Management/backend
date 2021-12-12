package com.experiment.controller;

import cn.hutool.core.util.IdUtil;
import com.experiment.common.Result;
import com.experiment.entity.*;
import com.experiment.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "课程相关")
@RestController("/course")
public class CourseController extends BaseController {
    @Resource
    UserService userService;
    @Resource
    CourseService courseService;
    @Resource
    ArrangeService arrangeService;
    @Resource
    DateService dateService;
    @Resource
    MessageService messageService;
    @ApiOperation("根据id查课")
    @GetMapping("/findCourse/id")
    public Result<?> findCourseById() {
        return courseService.idFindCourse(getUserId());
    }

    @ApiOperation("根据时间查课")
    @GetMapping("/findCourse/time")
    public Result<?> findCourseByTime(@RequestParam User user, @RequestParam Date date1, @RequestParam Date date2,
                                      @RequestParam int time1, @RequestParam int time2) {
        return courseService.timeFindCourse(user, date1, date2, time1, time2);
    }

    @ApiOperation("根据地点查课")
    @GetMapping("/findCourse/room")
    public Result<?> findCourseByRoom(@RequestParam String roomId) {
        return courseService.addressFindCourse(roomId);
    }

    @ApiOperation("获取所有开放性实验")
    @GetMapping("/findCourse/open")
    public Result<?> getAllOpenCourse() {
        return courseService.getOpenCourse();
//        ok
    }

    @GetMapping("/teacherCourse")
    public Result<?> getTeacherCourse(){
        return courseService.getTeacherCourse(getUserId());
    }


    @ApiOperation("增加课程/竞赛")
    @PostMapping("/addCourse")
    public Result<?> addCourse(@RequestBody MultipartFile file, @RequestParam String courseName,@RequestParam String courseContent,@RequestParam Boolean isOpening){
        System.out.println(courseContent);
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCourseContent(courseContent);
        course.setIsOpening(isOpening);
        course.setCreateTime(new Date());
        course.setTeacherId(getUserId());
        String courseId = IdUtil.fastSimpleUUID();
        course.setCourseId(courseId);
        if(getUser().getUserType().equals("STUDENT"))
            return Result.error("-1", "无权限");
        if (course.getIsOpening())
            return courseService.addCourse(course);
        Result<?> fileResult = userService.readUsersByExcel(file);
        if (fileResult.getCode().equals("-1"))
            return fileResult;

        Result<?> addResult = courseService.addCourse(course);
        if (addResult.getCode().equals("-1"))
            return addResult;
        List<User> studentList = (List<User>) fileResult.getData();
        Result<?> finalResult = courseService.addStudentList(course, studentList);
        if (finalResult.getCode().equals("-1")) {
            courseService.deleteCourse(courseId);
        }
        return finalResult;
    }

    @ApiOperation("删除课程")
    @PostMapping("/deleteCourse")
    public Result<?> deleteCourse(@RequestBody String courseId) {
        return courseService.deleteCourse(courseId);
    }

    @ApiOperation("更新课程信息")
    @PostMapping("/updateCourse")
    public Result<?> updateCourse(@RequestBody Course course) {
        return courseService.updateCourse(course);
    }

    @ApiOperation("获取学生名单")
    @GetMapping("/studentList")
    public Result<?> getStudentList(@RequestParam String courseId){
        Course course = new Course();
        course.setCourseId(courseId);
        if(getUser().getUserType().equals("STUDENT"))
            return Result.error("-1", "无权限");
        return courseService.getStudentList(course);
    }

    @ApiOperation("获取课程安排，教师、管理员以ArrRoom给出，学生以CourseTable给出")
    @GetMapping("/arrangement/user")
    public Result<?> getArrangement() {
        if (getUser().getUserType().equals("STUDENT"))
            return arrangeService.getStudentArrangement(getUserId());
        return arrangeService.getTeacherArrangement(getUserId());
    }

    @ApiOperation("获取某时间所有房间的课程安排")
    @GetMapping("/arrangement/roomCourse/all")
    public Result<?> getAllArrangement(@RequestParam Date date, @RequestParam Integer time) {
        return arrangeService.getArrangementByTime(date, time);  // 对象数组Object[List<Room>, List<List<Course>>]
    }

    @ApiOperation("管理员获取所有的课程安排")
    @GetMapping("/arrangement/all")
    public Result<?> getAllArrangement() {
        if (!getUser().getUserType().equals("ADMIN"))
            return Result.error("-1", "无权限");
        return arrangeService.getAllArrangement();
    }

    @ApiOperation("插入课程安排")
    @PostMapping("arrangement/add/course")
    public Result<?> addCourseArrangement(@RequestParam String courseId, @RequestParam Integer startWeek, @RequestParam Integer lastWeek, @RequestParam Integer day, @RequestParam Integer arrangeTime,
                                          @RequestParam Boolean isSharable,
                                          @RequestBody List<Room> rooms){
        Course course = new Course();
        course.setCourseId(courseId);
        List<Date> dates = new ArrayList<Date>();
        for(int i=startWeek;i<=lastWeek;i++){
            dates.add(dateService.getDateByWeekAndDay(i,day));
        }
        if(getUser().getUserType().equals("STUDENT"))
            return Result.error("-1", "无权限");
        for (Date date : dates) arrangeService.addArrangement(course, date, arrangeTime, rooms, isSharable);
        return Result.success();
    }

    @ApiOperation("插入竞赛安排")
    @PostMapping("/arrangement/add/compete")
    public Result<?> addCompeteArrangement(@RequestParam String courseId, @RequestParam Integer startWeek, @RequestParam Integer lastWeek, @RequestParam Integer day, @RequestParam Integer arrangeTime,
                                           @RequestParam Boolean isSharable,
                                           @RequestBody List<Room> rooms){
        if(getUser().getUserType().equals("STUDENT"))
            return Result.error("-1", "无权限");
        Course course = new Course();
        Course tmpCourse = new Course();
        course.setCourseId(courseId);
        List<Date> dates = new ArrayList<Date>();
        for(int i=startWeek;i<=lastWeek;i++){
            dates.add(dateService.getDateByWeekAndDay(i,day));
        }
        final String title = "非常抱歉，尽管系统已经尽力阻止，但因为管理员执意要占掉你的课程，您的原定课程安排已被更改，请在您的安排界面查看变动课程";
        Message message = new Message();
        message.setSendTime(new Date());
        for (Date date : dates){
            List<ArrCourse> arrCourses = (List<ArrCourse>) arrangeService.clearArrangementByTime(date, arrangeTime).getData();
            for(ArrCourse arrCourse : arrCourses){
                tmpCourse.setCourseId(arrCourse.getCourseId());
                message.setContent(title + "，变动课程编号为"+arrCourse.getCourseId());
                messageService.sendMessage(tmpCourse, message);
            }
        }
        addCourseArrangement(courseId, startWeek, lastWeek, day, arrangeTime, isSharable, rooms);
        return Result.success();
    }

    @ApiOperation("选择开放性实验")
    @PostMapping("arrangement/chooseOpenCourse")
    public Result<?> chooseOpenCourseArrangement(@RequestParam String courseId, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date, @RequestParam Integer time,
                                          @RequestParam String roomId) {
        System.out.println(courseId);
        Course course  = new Course();
        course.setCourseId(courseId);
        Room room =  new Room();
        room.setRoomId(roomId);
        if (!getUser().getUserType().equals("STUDENT"))
            return Result.error("-1", "无权限");
        return arrangeService.chooseOpenCourse(getUser(), course, room, date, time);
    }

    @ApiOperation("退选开放实验")
    @PostMapping("/arrangement/quitOpenCourse")
    public Result<?> quitOpenCourseArrangement(@RequestBody Date date, @RequestBody Integer time) {
        return arrangeService.quitOpenCourse(getUser(), date, time);
    }

    @ApiOperation("教师根据课程id查课")
    @GetMapping("/arrangement/course")
    public Result<?> getArrCourseByCourseId(@RequestParam String courseId) {
        if(getUser().getUserType().equals("STUDENT"))
            return Result.error("-1", "无权限");
        return courseService.getArrCourseById(courseId);
    }
}