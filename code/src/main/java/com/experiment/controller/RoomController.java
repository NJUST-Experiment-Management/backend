package com.experiment.controller;

import cn.hutool.core.util.IdUtil;
import com.experiment.common.Result;
import com.experiment.entity.ArrUser;
import com.experiment.entity.Course;
import com.experiment.entity.Device;
import com.experiment.entity.Room;
import com.experiment.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
    @Resource
    UserService userService;

    @ApiOperation("获取所有机房的列表")
    @GetMapping("/room/all")
    public Result<?> getAllRooms(){
        return roomService.getRooms();
    }

    @ApiOperation("获取所有可用的机房列表，含有机位信息")
    @GetMapping("/room/available")//ok(考试)
    public Result<?> getAllAvailableRoom(){
        List<Date> dateList = new ArrayList<>();
        dateList.add(new Date());
        List<Room> roomList = (List<Room>) roomService.getRoomByTime(dateList, 0, true).getData();
        deviceService.getAvailableDevice(roomList, new Date(), 0);
        return Result.success(roomList);
    }

    @ApiOperation("根据时间段查可用机房")
    @GetMapping("/room/time")
    public Result<?> getRoomWithTime(@RequestParam Integer week, @RequestParam Integer day, @RequestParam Integer arrangeTime,
                                     @RequestParam Boolean isShareable){
        return roomService.getRoomByTime(week, day, arrangeTime, isShareable);
    }

    @ApiOperation("根据时间段查可用机房")
    @GetMapping("/room/period")//ok
    public Result<?> getRoomWithPeriod(@RequestParam Integer startWeek, @RequestParam Integer lastWeek, @RequestParam Integer day, @RequestParam Integer arrangeTime,
                                       @RequestParam Boolean isSharable){
        List<Date> dateList = new ArrayList<>();
        for(int i = startWeek; i <= lastWeek; i++){
            dateList.add(dateService.getDateByWeekAndDay(i, day));
        }
        return roomService.getRoomByTime(dateList, arrangeTime, isSharable);
    }

    @ApiOperation("返回指定时间可供开放性课程选课的房间")
    @PostMapping("/room/openCourse")//ok
    public Result<?> getRoomsByOpenCourse(@RequestBody Course course, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date, @RequestParam Integer time){
        return roomService.getRoomByOpenCourse(course, date, time);
    }

    @ApiOperation("增加新的机房")
    @PostMapping("/room/add")//ok
    public Result<?> addRoom(@RequestBody Room room){
        room.setRoomId(IdUtil.fastSimpleUUID());
        return roomService.addRoom(room);
    }

    @ApiOperation("开关机房")//ok
    @PostMapping("/changeStatus/room")
    public Result<?> changeRoomStatus(@RequestBody Room room){
        if(!getUser().getUserType().equals("ADMIN"))
            return Result.error("-1", "无权限");
        if(room.getRoomStatus().equals("DISABLED"))
            room.setRoomStatus("AVAILABLE");
        else
            room.setRoomStatus("DISABLED");
        return roomService.updateRoom(room);
    }

    @ApiOperation("改变器械状态")//ok
    @PostMapping("/changeStatus/device")
    public Result<?> changeDeviceStaus(@RequestBody Device device){
        if(!getUser().getUserType().equals("ADMIN"))
            return Result.error("-1", "无权限");
        if(device.getDeviceStatus().equals("DISABLED"))
            device.setDeviceStatus("AVAILABLE");
        else
            device.setDeviceStatus("DISABLED");
        return deviceService.updateDeviceById(device);
    }

    @ApiOperation("获取机位历史记录")
    @GetMapping("/device/history")
    public Result<?> getDeviceHistory(@RequestParam String deviceId, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date arrangeDate, @RequestParam Integer arrangeTime){
        if(!getUser().getUserType().equals("ADMIN"))
            return Result.error("-1", "无权限");
        List<ArrUser> data = (List<ArrUser>) arrangeService.getArrangementByDeviceId(deviceId, arrangeDate, arrangeTime).getData();
        if(data.size() == 0)
            return Result.success(null);
        return userService.getUserById(data.get(0).getUserId());
    }


    @ApiOperation("获取某房间内所有的机位")
    @GetMapping("/device/all")//ok
    public Result<?> getDevicesByRoom(@RequestParam String roomId){
        return deviceService.getAllDevice(roomId);
    }

    @ApiOperation("更改房间内机位的状态")
    @PostMapping("/device/update")//ok
    public Result<?> updateDevice(@RequestBody Device device){
        return deviceService.updateDeviceById(device);
    }
}
