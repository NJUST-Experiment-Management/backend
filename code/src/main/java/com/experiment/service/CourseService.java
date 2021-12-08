package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.User;

import java.util.Date;

public interface CourseService {
    Result<?> IdFindCourse(String userId);

    /**
     * <p>传入course，向数据库插入course元组 </p>
     *
     * @param course 将插入Course实体，CourseId由数据库生成
     * @return 插入结果，若插入失败返回错误码
     */
    Result<?> addCourse(Course course);
    Result<?> deleteCourse(String courseId);
    Result<?> TimeFindCourse(User user,Date date1,Date date2,int time1,int time2);
    Result<?> AddressFindCourse(String arrRoomId);

    /**
     * <p>获取该课程的所有学生ID</p>
     *
     * @param course 目标课程
     * @return Result，封装有List&lt;String&gt;
     */
    Result<?> getStudentList(Course course);

    /**
     * <p>返回所有开放性实验</p>
     *
     * @return Result，List-Course
     */
    Result<?> getOpenCourse();
}
