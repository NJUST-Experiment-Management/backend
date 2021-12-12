package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.DateMsg;
import com.experiment.entity.Term;

import java.util.Date;

public interface DateService {
        Result<?> addTerm(Term term);
        Result<?> getDateList(Date date,Integer times);
        Date getDateByWeekAndDay(Integer week,Integer day);
        DateMsg getDateWeekTimeAndTerm(Date date);

}
