package com.experiment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.experiment.entity.Course;
import com.experiment.entity.CourseStudent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseStudentMapper extends BaseMapper<CourseStudent> {
}
