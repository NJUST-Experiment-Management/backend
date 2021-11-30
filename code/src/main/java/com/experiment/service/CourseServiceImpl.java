package com.experiment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.experiment.common.Result;
import com.experiment.entity.ArrCourse;
import com.experiment.entity.Course;
import com.experiment.entity.User;
import com.experiment.mapper.ArrCourseMapper;
import com.experiment.mapper.ArrUserMapper;
import com.experiment.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{
    @Resource
    CourseMapper courseMapper;
    @Resource
    ArrUserMapper arrUserMapper;
    @Resource
    ArrCourseMapper arrCourseMapper;
    public Result<?> studentIdFindCourse(String userId){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        List<ArrCourse> arrCourseList=(List<ArrCourse>) arrCourseMapper.getStudentAllArrCourse(userId);
        if (arrCourseList == null) {
            return Result.error("-1", "无课程安排");
        }
        return Result.success(arrCourseList);
    }

    public Result<?> TimeFindCourse(User user, Date date1, Date date2){
        String userType=user.getUserType();
        if(userType.equals("TEACHER")){
            List<ArrCourse> arrCourseList=(List<ArrCourse>) arrCourseMapper.getTeacherAllTimeArrCourse(user.getUserId(),date1,date2);
            if(arrCourseList==null){
                return Result.error("-1","无课程安排");
            }
            return Result.success(arrCourseList);
        }
        else if(userType.equals("STUDENT")){
            List<ArrCourse> arrCourseList=(List<ArrCourse>) arrCourseMapper.getStudentAllTimeArrCourse(user.getUserId(),date1,date2);
            if(arrCourseList==null){
                return Result.error("-1","无课程安排");
            }
            return Result.success(arrCourseList);
        }
        else if(userType.equals("ADMIN")){
            List<ArrCourse> arrCourseList=(List<ArrCourse>) arrCourseMapper.getAdminAllTimeArrCourse(user.getUserId(),date1,date2);
            if(arrCourseList==null){
                return Result.error("-1","无课程安排");
            }
            return Result.success(arrCourseList);
        }
        return Result.success();
    }

    public Result<?> addCourse(Course course){
        if(courseMapper.insert(course)!=0)
            return  Result.success();
        else
            return Result.error("-1","添加课程失败");
    }

    public Result<?> deleteCourse(Course course){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("courseId",course.getCourseId());
        if(courseMapper.deleteById(course.getCourseId())!=0)
            return Result.success();
        else
            return Result.error("-1","删除课程失败");
    }


}
