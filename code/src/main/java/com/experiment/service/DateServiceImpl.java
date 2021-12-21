package com.experiment.service;

import cn.hutool.core.date.DateUtil;
import com.experiment.common.Result;
import com.experiment.entity.Term;
import com.experiment.entity.DateMsg;
import com.experiment.mapper.TermMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class DateServiceImpl implements DateService{

    @Resource
    TermMapper termMapper;
    @Override
    public Result<?> addTerm(Term term) {
        if(termMapper.insert(term)>0){
            return Result.success();
        }
        else{
            return Result.error("-1","学期插入失败");
        }
    }

    @Override
    public Result<?> getDateList(Date date, Integer times) {
        List<Date> dateList = null;
        dateList.add(date);
        while (times>1){
            date= DateUtil.offsetDay(date,7);
            /** 不确定这个函数的使用是否正确   7 or -7*/
            dateList.add(date);
            times=times-1;
        }
        return Result.success(dateList);
    }

    @Override
    public Date getDateByWeekAndDay(Integer week, Integer day) {
        Date currentDate=DateUtil.date();
        Term term=termMapper.getNearTerm(currentDate);
        Date date=DateUtil.offsetWeek(term.getBeginTime(),week-1);
//        Integer dayOfDate=DateUtil.dayOfWeek(date);
        if(day==0)
            day=7;
//        if(dayOfDate==0)
//            dayOfDate=7;
        Integer dayDistance=day-1;
        date=DateUtil.offsetDay(date,dayDistance);
        return date;

    }

    @Override
    public DateMsg getDateWeekTimeAndTerm(Date date) {
        Term term=termMapper.getNearTerm(date);
        Integer weekTime=0;
        /**@Param weekTime 周次 */

        Integer dayOfWeekTerm= DateUtil.dayOfWeek(term.getBeginTime());
        /**@Param dayOfweekTerm 是这学期第一天是星期几 */

        Integer dayOfWeek= DateUtil.dayOfWeek(date);
        /** @param dayOfWeek 该日期是星期几*/

        long dayDistance=DateUtil.betweenDay(date,term.getBeginTime(),true);
        /** @param dayDistance 两天的日期差距*/

        if(dayOfWeekTerm==0){
            dayDistance=dayDistance+6;
            weekTime= Math.toIntExact(dayDistance / 7 + 1);
        }
        else if(dayOfWeekTerm==1){
            weekTime= Math.toIntExact(dayDistance / 7 + 1);
        }
        else{
            weekTime= Math.toIntExact(dayDistance / 7 + 1);
            if(dayDistance%7+dayOfWeekTerm>7)
                weekTime=weekTime+1;
        }
        DateMsg dateMsg=new DateMsg(date,term,weekTime);
        return dateMsg;
    }
}
