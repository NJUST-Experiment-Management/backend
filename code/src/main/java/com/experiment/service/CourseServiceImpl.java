package com.experiment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.experiment.common.Result;
import com.experiment.entity.*;
import com.experiment.mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//TODO 大写方法我没检查，先纠正一下命名规范，然后用test里面的类自查一下/bt
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
    @Resource
    CourseStudentMapper courseStudentMapper;
    @Resource
    ArrRoomMapper arrRoomMapper;

    @Override
    public Result<?> idFindCourse(String userId){
        List<CourseTable> courseTableList=(List<CourseTable>) courseTableMapper.IdFindCourse(userId);
        if (courseTableList == null) {
            return Result.error("-1", "无课程安排");
        }
        return Result.success(courseTableList);
    }

    @Override
    public Result<?> addressFindCourse(String roomId){
        List<ArrCourse> arrCourseList= arrCourseMapper.addressFindCourse(roomId);
        if(arrCourseList==null)
            return Result.error("-1","无课程信息");
        else
            return Result.success(arrCourseList);
    }

    @Override
    public Result<?> timeFindCourse(User user, Date date1, Date date2, int time1, int time2){
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

    @Override
    public Result<?> addCourse(Course course){
        if(courseMapper.insert(course) > 0)
            return Result.success();
       else
           return Result.error("-1", "插入数据失败");
    }

    @Override
    public Result<?> addStudentList(Course course, List<User> students) {
        for(User user : students){
            if(courseStudentMapper.insert(new CourseStudent(course.getCourseId(), user.getUserId())) <= 0){
                courseStudentMapper.delete(new QueryWrapper<CourseStudent>()
                        .eq("course_id", course.getCourseId()));
                return Result.error("-1", "导入错误，请检查excel文件内参数是否正确，或刷新页面后重试");
            }
        }
        return Result.success();
    }

    @Override
    public Result<?> updateCourse(Course course) {
        return Result.success(courseMapper.updateById(course));
    }

    public Result<?> deleteCourse(String courseId){
        //TODO 这部分要干啥子？删除课程的话外键约束可以一起删掉一堆，是说有些情况不准删课吗/yiw
        /*
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
         */
        courseMapper.deleteById(courseId);
        return Result.success();
    }

    @Override
    public Result<?> getStudentList(Course course) {
        String courseId=course.getCourseId();
        QueryWrapper<CourseStudent> queryWrapper=new QueryWrapper<CourseStudent>()
                .eq("course_id",courseId);
        List<CourseStudent> courseStudentsList=courseStudentMapper.selectList(queryWrapper);
        List<String> studentIdList=new ArrayList<String>();
        for (int i=0;i<courseStudentsList.size();i++){
            studentIdList.add(courseStudentsList.get(i).getStudentId());
        }
        return Result.success(studentIdList);
    }

    @Override
    public Result<?> getOpenCourse() {
        QueryWrapper<Course> queryWrapper=new QueryWrapper<Course>()
                .eq("is_opening","1");
        List<Course> openCourseList=courseMapper.selectList(queryWrapper);
        return Result.success(openCourseList);
    }

    @Override
    public Result<?> getArrCourseById(String courseId) {
        return Result.success(arrCourseMapper.getArrCourseByCourseId(courseId));
    }

}
