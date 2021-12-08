package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.Room;

import java.util.Date;
import java.util.List;

public interface RoomService {
    /**
     * <p>根据时间，返回该时间可用机房列表</p>
     *
     * @param week 待查询周次
     * @param day 带查询日（以星期方式给出）
     * @param time 待查询时间段（以大节方式给出）
     * @param isShareable 是否查询可共享机房
     * @return Result，封装可用机房列表
     */
    Result<?> getRoomByTime(Integer week, Integer day, Integer time, Boolean isShareable);

    /**
     * <p>根据时间段，查询该时间段可用机房列表</p>
     *
     * @param time 目标大节
     * @param isShareable 是否返回可共享机房
     * @return Result，含有机房列表
     */
    Result<?> getRoomByTime(List<Date> dateList, Integer time, Boolean isShareable);

    /**
     * <p>查询某时间段的开放性课程有无占领的教室</p>
     *
     * @param course 课程
     * @param date 日期
     * @param time 大节
     * @return Result, List-Room
     */
    Result<?> getRoomByOpenCourse(Course course, Date date, Integer time);

    /**
     * <p>从数据库更新目标房间</p>
     *
     * @param room 目标房间
     * @return Result, 返回成功信息
     */
    Result<?> updateRoom(Room room);

    /**
     * <p>根据roomList，填充被占用座位</p>
     *
     * @param roomList 目标room列表
     * @return Result，封装填充好的room
     */
    Result<?> getLeftStudentNumber(List<Room> roomList);


    Result<?> getAvailableRoom(List<Date> dateList, Boolean isSharable);
}
