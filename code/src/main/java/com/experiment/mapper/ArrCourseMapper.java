package com.experiment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.experiment.entity.ArrCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
@Mapper
public interface ArrCourseMapper extends BaseMapper<ArrCourse> {
    List<ArrCourse> addressFindCourse(String roomId);
    List<String> getArrCourseByCourseIdAndTime(@Param("course_id") String courseId,@Param("date") Date date,@Param("time") Integer time);
}