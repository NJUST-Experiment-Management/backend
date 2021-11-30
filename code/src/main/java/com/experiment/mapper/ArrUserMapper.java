package com.experiment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.experiment.entity.ArrUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArrUserMapper extends BaseMapper<ArrUser> {
    List<ArrUser> studentIdFindCourse(String userId);
}
