package com.experiment.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.experiment.common.Result;
import com.experiment.entity.*;
import com.experiment.mapper.ArrCourseMapper;
import com.experiment.mapper.ArrRoomMapper;
import com.experiment.mapper.RoomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoomServiceImpl implements RoomService{
    @Resource
    RoomMapper roomMapper;
    @Resource
    ArrRoomMapper arrRoomMapper;
    @Resource
    DateService dateService;
    @Resource
    DeviceService deviceService;
    @Resource
    ArrCourseMapper arrCourseMapper;

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
        List<Room> roomList=null;
        HashMap<Room, Integer> mp = new HashMap<Room,Integer>();
        for(Date date:dateList){
            DateMsg dg=dateService.getDateWeekTimeAndTerm(date);
            roomList= (List<Room>) getRoomByTime(dg.getWeektime(), DateUtil.dayOfWeek(date),time,isShareable).getData();
            for(Room x:roomList){
                if(!mp.containsKey(x))
                    mp.put(x,1);
                int cnt = mp.get(x)+1;
                mp.put(x,cnt);
            }
        }
        int cnt = 0;
        for(Map.Entry<Room, Integer> it:mp.entrySet()){
            cnt = cnt>it.getValue()?cnt:it.getValue();
        }
        List<Room> res = new ArrayList<>();
        for(Map.Entry<Room, Integer> it:mp.entrySet()){
            if(cnt == it.getValue())
                res.add(it.getKey());
        }
        return Result.success(res);
    }

    public Result<?> getRoomByOpenCourse(Course course, Date date, Integer time){
        List<Room> roomList_out=new ArrayList<>();
        List<String> courseIdList=arrCourseMapper.getArrCourseByCourseIdAndTime(course.getCourseId(),date,time);
        if(courseIdList!=null){
            List<Room> roomList = new ArrayList<>();
            for(int i=0;i<courseIdList.size();i++){
                Room room=new Room();
                room.setRoomId(courseIdList.get(i));
                roomList.add(room);
            }
            Result result=getLeftStudentNumber(roomList,date,time);
            List<Room> roomList_temp=(List<Room>) result.getData();
            for(int i=0;i<roomList_temp.size();i++){
                if(roomList_temp.get(i).getOccupiedDevice()>=3){
                    roomList_out.add(roomList_temp.get(i));
                }
            }
            if(roomList_out.size() > 0){
                return Result.success(roomList_out);
            }
        }
        List<Date> dateList=new ArrayList<>();
        dateList.add(date);
        //todo 查找机房的时候只查找了可共享的，没用的能不能查到
        Result result=getRoomByTime(dateList,time, Boolean.TRUE);
        System.out.println(result);
        List<Room> avialableListRoom=(List<Room>)result.getData();
        for(int i=0;i<avialableListRoom.size();i++){
            if(avialableListRoom.get(i).getOccupiedDevice()>=3){
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
        return Result.success(roomMapper.selectList(null));
    }

    @Override
    public Result<?> getRoomByTime(Integer week, Integer day, Integer time, Boolean isShareable) {
        Date date=dateService.getDateByWeekAndDay(week,day);
        List<Room> roomList=new ArrayList<>();
        List<Room> room= roomMapper.selectList(null);
        List<Room> rooms= (List<Room>) getLeftStudentNumber(room,date,time).getData();
        for(Room i:rooms){
            System.out.println("xxxxxxxx");
            if(i.getRoomStatus()!="DISABLED"){
                QueryWrapper<ArrRoom> queryWrapper = new QueryWrapper<ArrRoom>()
                        .eq("room_id",i.getRoomId())
                        .eq("arrange_date",date)
                        .eq("arrange_time",time);
                ArrRoom arrRoom = arrRoomMapper.selectOne(queryWrapper);
                if(arrRoom == null){
                    roomList.add(i);
                }
                else if(isShareable&&arrRoom.isShareable()&&i.getOccupiedDevice()>0)
                    roomList.add(i);
//
            }
        }
        return Result.success(roomList);

    }

    //TODO 更进一步，当状态变为不可用时，给将要在这里上课的全体人员发送通知，并删除相关安排
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
        if(roomMapper.insert(room)<0)
            return Result.error("-1", "插入机房失败，请刷新后重试");
        return deviceService.addDevice(room);
    }
}
