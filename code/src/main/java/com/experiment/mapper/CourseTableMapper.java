package com.experiment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.experiment.entity.CourseTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CourseTableMapper extends BaseMapper<CourseTable> {
    List<CourseTable> IdFindCourse(String userId);
    List<CourseTable> getIdAllTimeCourse(String userId, Date date1, Date date2, int time1, int time2);
    List<CourseTable> getAdminAllTimeArrCourse(Date date1, Date date2, int time1, int time2);
    List<CourseTable> courseIdFindCourse(String courseId);
}
