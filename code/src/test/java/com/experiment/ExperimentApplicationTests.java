package com.experiment;

import com.experiment.mapper.CourseMapper;
import com.experiment.mapper.RoomMapper;
import com.experiment.mapper.UserMapper;
import com.experiment.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExperimentApplicationTests {
    @Resource
    ArrangeService arrangeService;
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
        Object data = arrangeService.getTeacherArrangement("20001").getData();
        System.out.println(data);
    }
}
