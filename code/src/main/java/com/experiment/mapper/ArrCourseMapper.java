package com.experiment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.experiment.entity.ArrCourse;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
@Mapper
public interface ArrCourseMapper extends BaseMapper<ArrCourse> {
    List<ArrCourse> getStudentAllArrCourse(String userId);
    List<ArrCourse> getTeacherAllTimeArrCourse(String userId, Date date1,Date date2);
    List<ArrCourse> getStudentAllTimeArrCourse(String userId, Date date1,Date date2);
    List<ArrCourse> getAdminAllTimeArrCourse(String userId, Date date1,Date date2);
}
