package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.*;
import com.experiment.mapper.ArrCourseMapper;
import com.experiment.mapper.RoomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class RoomServiceImpl implements RoomService{
    @Resource
    RoomMapper roomMapper;
    @Resource
    DateService dateService;
    @Resource
    DeviceService deviceService;

    public Result<?> getLeftStudentNumber(List<Room> roomList, Date date, Integer time){
        for(int i = 0; i < roomList.size(); i++){
            List<Room> tmpRoom = new ArrayList<>();
            tmpRoom.add(roomList.get(i));
            List<Device> temp = (List<Device>) deviceService.getAvailableDevice(tmpRoom, date, time).getData();
            roomList.get(i).setOccupiedDevice(temp.size());
        }
        return Result.success(roomList);
    }

    public Result<?> getRoomByTime(List<Date> dateList, Integer time, Boolean isShareable){
        return Result.success();
    }
    @Resource
    ArrCourseMapper arrCourseMapper;
    public Result<?> getRoomByOpenCourse(Course course, Date date, Integer time){
        List<Room> roomList_out=new ArrayList<>();
        List<String> courseIdList=arrCourseMapper.getArrCourseByCourseIdAndTime(course.getCourseId(),date,time);
        if(courseIdList!=null){
            //todo 将时间转换为week，day
            List<Room> roomList = new ArrayList<>();
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
        List<Date> dateList=new ArrayList<>();
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
    public Result<?> getRooms() {
        return Result.success(roomMapper.getRooms());
    }
    @Override
    public Result<?> getRoomByTime(Integer week, Integer day, Integer time, Boolean isShareable) {
        Date date=dateService.getDateByWeekAndDay(week,day);
        List<Room> roomList=new ArrayList<>();
        List<Room> room=roomMapper.getRooms();
        List<Room> rooms= (List<Room>) getLeftStudentNumber(room,date,time).getData();
        for(Room i:rooms){
            ArrRoom arrRoom=new ArrRoom(null,i.getRoomId(),null,date,time,isShareable);
            /** courseId:为知 @param*/
            if(roomMapper.getOkRoomNum(arrRoom)==0){
                roomList.add(i);
            }
            else if(isShareable==true&&i.getOccupiedDevice()<i.getRoomCol()*i.getRoomRow()){
                roomList.add(i);
            }

        }
        return Result.success(roomList);

    }

    public Result<?> updateRoom(Room room) {
        if(roomMapper.updateById(room)>0){
            return Result.success();
        }
        else  {
            return Result.error("-1","机房更新失败");
        }
    }

    @Override
    public Result<?> addRoom(Room room) {
        if(roomMapper.insert(room)>0){
            return Result.success();
        }
        else {
            return  Result.error("-1","机房插入失败");
        }
    }

    @Override
    public Result<?> getAvailableRoom(List<Date> dateList, Boolean isSharable) {
        return null;
    }
}
