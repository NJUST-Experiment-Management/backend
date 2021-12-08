package com.experiment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.experiment.common.Result;
import com.experiment.entity.*;
import com.experiment.mapper.*;
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
    @Resource
    CourseTableMapper courseTableMapper;
    public Result<?> IdFindCourse(String userId){
        List<CourseTable> courseTableList=(List<CourseTable>) courseTableMapper.IdFindCourse(userId);
        if (courseTableList == null) {
            return Result.error("-1", "无课程安排");
        }
        return Result.success(courseTableList);
    }

    public Result<?> AddressFindCourse(String roomId){
        List<ArrCourse> arrCourseList=(List<ArrCourse>) arrCourseMapper.AddressFindCourse(roomId);
        if(arrCourseList==null)
            return Result.error("-1","无课程信息");
        else
            return Result.success(arrCourseList);
    }

    public Result<?> TimeFindCourse(User user, Date date1, Date date2,int time1,int time2){
        String userType=user.getUserType();
        if(userType.equals("TEACHER") ||userType.equals("STUDENT")){
            List<CourseTable> courseTableList=(List<CourseTable>) courseTableMapper.getIdAllTimeCourse(user.getUserId(),date1,date2,time1,time2);
            if(courseTableList==null){
                return Result.error("-1","无课程安排");
            }
            return Result.success(courseTableList);
        }
        else if(userType.equals("ADMIN")){
            List<CourseTable> courseTableList=(List<CourseTable>) courseTableMapper.getAdminAllTimeArrCourse(date1,date2,time1,time2);
            if(courseTableList==null){
                return Result.error("-1","无课程安排");
            }
            return Result.success(courseTableList);
        }
        return Result.error("-1","身份错误");
    }

    public Result<?> addCourse(Course course){
        if(courseMapper.insert(course) > 0)
            return Result.success();
       // return Result.error("-1", "插入数据失败");
    }

    @Resource
    ArrRoomMapper arrRoomMapper;
    public Result<?> deleteCourse(String courseId){
        QueryWrapper<ArrRoom> queryWrapper = new QueryWrapper<>();
        List<CourseTable> courseTableList=courseTableMapper.courseIdFindCourse(courseId);
        if(courseMapper.deleteById(courseId)!=0){
            if(arrUserMapper.deleteById(courseId)!=0){
                for(int i=0;i<courseTableList.size();i++){
                    queryWrapper.eq("arrange_date",courseTableList.get(i).getArrangeDate()).eq("arrange_time",courseTableList.get(i).getArrangeTime());
                    arrRoomMapper.delete(queryWrapper);
                }
                return Result.success();
            }
            else
                return Result.error("-1","删除课程失败");
        }
        else
            return Result.error("-1","删除课程失败");
    }
}
