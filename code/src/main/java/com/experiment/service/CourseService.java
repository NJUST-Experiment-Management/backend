package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.User;

import java.util.Date;
import java.util.List;

public interface CourseService {
    Result<?> idFindCourse(String userId);
    Result<?> getTeacherCourse(String teacherId);
    /**
     * <p>传入course，向数据库插入course元组 </p>
     *
     * @param course 将插入Course实体，CourseId由数据库生成
     * @return 插入结果，若插入失败返回错误码
     */
    Result<?> addCourse(Course course);

    /**
     * <p>传入新Course，以ID为标识更新旧Course</p>
     *
     * @param course 已被更改的Course
     * @return 更改结果
     */
    Result<?> updateCourse(Course course);

    /**
     * <p>传入course和学生id列表，向数据库插入选课信息</p>
     *
     * @param course 目标课程
     * @param students 目标学生列表
     * @return 成功与否
     */
    Result<?> addStudentList(Course course, List<User> students);

    /**
     * <p>传入课程id，删除课程</p>
     *
     * @param courseId 课程id
     * @return 删除结果
     */
    Result<?> deleteCourse(String courseId);
    Result<?> timeFindCourse(User user, Date date1, Date date2, int time1, int time2);
    Result<?> addressFindCourse(String roomId);

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
