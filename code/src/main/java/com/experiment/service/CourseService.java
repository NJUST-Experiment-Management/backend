package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.User;

import java.util.Date;

public interface CourseService {
    Result<?> IdFindCourse(String userId);
    Result<?> addCourse(Course course);
    Result<?> deleteCourse(String courseId);
    Result<?> TimeFindCourse(User user,Date date1,Date date2,int time1,int time2);
    Result<?> AddressFindCourse(String arrRoomId);
}
