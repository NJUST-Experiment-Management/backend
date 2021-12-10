package com.experiment.controller;

import com.experiment.common.Result;
import com.experiment.entity.Device;
import com.experiment.entity.Room;
import com.experiment.service.DeviceService;
import com.experiment.service.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "机房相关")
@RestController
public class RoomController extends BaseController{

    @Resource
    RoomService roomService;

    @Resource
    DeviceService deviceService;

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
        return null;
      //  return roomService.getRoomByTime(startWeek, lastWeek, day, arrangeTime, isSharable);
    }

    @ApiOperation("改变机房状态")
    @PostMapping("/changeStatus/room")
    public Result<?> changeRoomStatus(@RequestParam Room room){
        return roomService.updateRoom(room);
    }

    @ApiOperation("改变器械状态")
    @PostMapping("/changeStatus/device")
    public Result<?> changeDeviceStaus(@RequestParam Device device){
        return deviceService.updateDeviceById(device);
    }
}
