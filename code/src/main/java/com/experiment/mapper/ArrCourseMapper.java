package com.experiment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.experiment.entity.ArrCourse;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
@Mapper
public interface ArrCourseMapper extends BaseMapper<ArrCourse> {
    List<ArrCourse> AddressFindCourse(String roomId);
    List<String> getArrCourseByCourseIdAndTime(String courseId, Date date, Integer time);
}