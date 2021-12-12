package com.experiment;

import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.mapper.CourseMapper;
import com.experiment.mapper.RoomMapper;
import com.experiment.mapper.UserMapper;
import com.experiment.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExperimentApplicationTests {
    @Resource
    ArrangeService arrangeService;
    @Resource
    RoomService roomService;
    @Resource
    CourseMapper courseMapper;
    @Resource
    UserService userService;
    @Resource
    UserMapper userMapper;
    @Resource
    RoomMapper roomMapper;

    @Test
    void contextLoads() throws Exception{
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
        Course course = new Course();
        course.setCourseId("50001");
        Object roomByOpenCourse = roomService.getRoomByOpenCourse(course, sdf.parse("2021-12-17"), 2).getData();
        System.out.println("---"+roomByOpenCourse);
    }
}
