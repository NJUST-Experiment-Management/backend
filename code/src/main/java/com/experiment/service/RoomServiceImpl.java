package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.ArrCourse;
import com.experiment.entity.Course;
import com.experiment.entity.Room;
import com.experiment.mapper.ArrCourseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class RoomServiceImpl implements RoomService{

    public Result<?> getLeftStudentNumber(List<Room> roomList, Date date, Integer time){
        return Result.success();
    }
    public Result<?> getRoomByTime(List<Date> dateList, Integer time, Boolean isShareable){
        return Result.success();
    }
    @Resource
    ArrCourseMapper arrCourseMapper;
    public Result<?> getRoomByOpenCourse(Course course, Date date, Integer time){
        List<Room> roomList_out=null;
        List<String> courseIdList=arrCourseMapper.getArrCourseByCourseIdAndTime(course.getCourseId(),date,time);
        if(courseIdList!=null){
            //todo 将时间转换为week，day
            List<Room> roomList = null;
            for(int i=0;i<courseIdList.size();i++){
                Room room=new Room();
                room.setRoomId(courseIdList.get(i));
                roomList.add(room);
            }
            Result result=getLeftStudentNumber(roomList,date,time);
            List<Room> roomList_temp=(List<Room>) result.getData();
            for(int i=0;i<roomList_temp.size();i++){
                if(roomList_temp.get(i).getRoomRow()*roomList_temp.get(i).getRoomCol()-roomList_temp.get(i).getOccupiedDevice()>=3){
                    roomList_out.add(roomList_temp.get(i));
                }
            }
            if(roomList_out!=null){
                return Result.success(roomList_out);
            }
        }
        List<Date> dateList=null;
        dateList.add(date);
        //todo 查找机房的时候只查找了可共享的，没用的能不能查到
        Result result=getRoomByTime(dateList,time, Boolean.TRUE);
        List<Room> avialableListRoom=(List<Room>)result.getData();
        for(int i=0;i<avialableListRoom.size();i++){
            if(avialableListRoom.get(i).getRoomRow()*avialableListRoom.get(i).getRoomCol()-avialableListRoom.get(i).getOccupiedDevice()>=3){
                roomList_out.add(avialableListRoom.get(i));
            }
        }
        if(roomList_out!=null){
            return Result.success(roomList_out);
        }
        return Result.error("-1","无可用教室");
    }

    @Override
    public Result<?> getRoomByTime(Integer week, Integer day, Integer time, Boolean isShareable) {
        return Result.success();
    }

    @Override
    public Result<?> updateRoom(Room room) {
        return Result.success();
    }
}
