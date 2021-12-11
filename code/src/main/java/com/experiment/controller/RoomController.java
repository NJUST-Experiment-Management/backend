package com.experiment.controller;

import cn.hutool.core.util.IdUtil;
import com.experiment.common.Result;
import com.experiment.entity.Course;
import com.experiment.entity.Device;
import com.experiment.entity.Room;
import com.experiment.service.ArrangeService;
import com.experiment.service.DateService;
import com.experiment.service.DeviceService;
import com.experiment.service.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "机房相关")
@RestController
public class RoomController extends BaseController{

    @Resource
    RoomService roomService;
    @Resource
    DeviceService deviceService;
    @Resource
    ArrangeService arrangeService;
    @Resource
    DateService dateService;

    @ApiOperation("获取所有机房的列表")
    @GetMapping("/room/all")
    public Result<?> getAllRooms(){
        return roomService.getRooms();
    }

    @ApiOperation("根据时间段查可用机房")
    @GetMapping("/room/time")
    public Result<?> getRoomWithTime(@RequestParam Integer week, @RequestParam Integer day, @RequestParam Integer arrangeTime,
                                     @RequestParam Boolean isShareable){
        return roomService.getRoomByTime(week, day, arrangeTime, isShareable);
    }

    @ApiOperation("根据时间段查可用机房")
    @GetMapping("/room/period")
    public Result<?> getRoomWithPeriod(@RequestParam Integer startWeek, @RequestParam Integer lastWeek, @RequestParam Integer day, @RequestParam Integer arrangeTime,
                                       @RequestParam Boolean isSharable){
        List<Date> dateList = new ArrayList<>();
        for(int i = startWeek; i <= lastWeek; i++){
            dateList.add(dateService.getDateByWeekAndDay(i, day));
        }
        return roomService.getRoomByTime(dateList, arrangeTime, isSharable);
    }

    @ApiOperation("返回指定时间可供开放性课程选课的房间")
    @GetMapping("/room/openCourse")
    public Result<?> getRoomsByOpenCourse(@RequestParam Course course, @RequestParam Date date, @RequestParam Integer time){
        return roomService.getRoomByOpenCourse(course, date, time);
    }

    @ApiOperation("增加新的机房")
    @PostMapping("room/add")
    public Result<?> addRoom(@RequestBody Room room){
        room.setRoomId(IdUtil.fastSimpleUUID());
        return roomService.addRoom(room);
    }

    @ApiOperation("改变机房状态")
    @PostMapping("/changeStatus/room")
    public Result<?> changeRoomStatus(@RequestBody Room room){
        return roomService.updateRoom(room);
    }

    @ApiOperation("改变器械状态")
    @PostMapping("/changeStatus/device")
    public Result<?> changeDeviceStaus(@RequestBody Device device){
        return deviceService.updateDeviceById(device);
    }



    @ApiOperation("获取某房间内所有的机位")
    @GetMapping("/device/all")
    public Result<?> getDevicesByRoom(@RequestParam String roomId){
        return deviceService.getAllDevice(roomId);
    }

    @ApiOperation("更改房间内机位的状态")
    @PostMapping("/device/update")
    public Result<?> updateDevice(@RequestBody Device device){
        return deviceService.updateDeviceById(device);
    }
}
