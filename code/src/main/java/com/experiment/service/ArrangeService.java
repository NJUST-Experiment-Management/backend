package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.Room;
import com.experiment.entity.User;

import java.util.Date;
import java.util.List;

public interface ArrangeService {
    Result<?> getAllArrangement();

    /**
     * <p>返回用户有的课程</p>
     *
     * @param studentId 目标学生
     * @return List-CourseTable
     */
    Result<?> getStudentArrangement(String studentId);

    /**
     * <p>返回老师对应的课程</p>
     *
     * @param teacherId 目标教师
     * @return List-CourseTable
     */
    Result<?> getTeacherArrangement(String teacherId);

    /**
     * <p>返回该房间上过的课程</p>
     *
     * @param roomId 目标房间
     * @return List-ArrCourse
     */
    Result<?> getArrangementByRoomId(String roomId);

    /**
     * <p>根据时间段查询课程<p>
     *
     * @param dateList 需要查询的时间段
     * @return Result，返回List-CourseTable
     */
    Result<?> getArrangementByTime(List<Date> dateList);

    /**
     * <p>根据大节和房间，查询这个时候上的课</p>
     *
     * @param date 日期
     * @param time 时间
     * @param roomId 房间
     * @return Result，List-ArrCourse
     */
    Result<?> getArrangementBtTimeRoom(Date date, Integer time, String roomId);

    /**
     * <p>按照某一精确时间查询对应房间及课程信息，请调用上面那个函数</p>
     *
     * @param date 日期
     * @param time 大节
     * @return Result，Object[List-Room, List-List-Course]
     */
    Result<?> getArrangementByTime(Date date, Integer time);

    /**
     * <p>插入课程安排</p>
     *
     * @param course 课程
     * @param date 日期
     * @param time 大节
     * @param roomList 房间列表
     * @return Result, 成功与否
     */
    Result<?> addArrangement(Course course, Date date, Integer time, List<Room> roomList, Boolean isShareable);

    /**
     * <p>学生选择开放性实验</p>
     *
     * @param student 学生信息
     * @param course 课程信息
     * @param room 房间信息
     * @param date 选择时间
     * @param time 选择大节
     * @return Result， 成功与否
     */
    Result<?> chooseOpenCourse(User student, Course course, Room room, Date date, Integer time);

    /**
     * <p>退选课程</p>
     *
     * @param student 学生信息
     * @param date 日期信息
     * @param time 大节信息
     * @return Result, 成功与否
     */
    Result<?> quitOpenCourse(User student, Date date, Integer time);
}
