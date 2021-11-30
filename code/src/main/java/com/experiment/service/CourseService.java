package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.User;
import com.experiment.entity.Course;

import java.util.Date;

public interface CourseService {
    Result<?> studentIdFindCourse(String userId);
    Result<?> addCourse(Course course);
    Result<?> deleteCourse(Course course);
    Result<?> TimeFindCourse(User user,Date date1,Date date2);
}
