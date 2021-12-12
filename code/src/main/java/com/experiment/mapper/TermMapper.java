package com.experiment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.experiment.entity.Term;

import java.util.Date;

public interface TermMapper extends BaseMapper<Term> {
    Term getNearTerm(Date date);
}
