package com.experiment.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.experiment.common.Result;
import com.experiment.entity.ArrUser;
import com.experiment.entity.Device;
import com.experiment.entity.Room;
import com.experiment.mapper.ArrUserMapper;
import com.experiment.mapper.DeviceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService{
    @Resource
    DeviceMapper deviceMapper;
    @Resource
    ArrUserMapper arrUserMapper;

    @Override
    public Result<?> getAvailableDevice(List<Room> roomList, Date date, Integer time) {
        QueryWrapper<ArrUser> queryWrapper=new QueryWrapper<ArrUser>()
                .eq("arrange_date",date)
                .eq("arrange_time",time);
        List<ArrUser> arrUsers = arrUserMapper.selectList(queryWrapper);

        List<String> occupiedDeviceId=new ArrayList<>();
        occupiedDeviceId.add("");
        List<String> roomIds = new ArrayList<>();
        roomIds.add("");
        for(ArrUser arrUser : arrUsers){
            occupiedDeviceId.add(arrUser.getDeviceId());
        }
        for(Room room : roomList){
            roomIds.add(room.getRoomId());
        }
        QueryWrapper<Device> queryWrapper1=new QueryWrapper<Device>()
                .eq("device_status","AVAILABLE")
                .in("room_id", roomIds)
                .notIn("device_id",occupiedDeviceId);
        List<Device> devices = deviceMapper.selectList(queryWrapper1);
        return Result.success(devices);
    }

    @Override
    public  Result<?> getAllDevice(String id){
        QueryWrapper<Device> queryWrapper=new QueryWrapper<Device>()
                .eq("room_id",id);
        List<Device> devices = deviceMapper.selectList(queryWrapper);
        return Result.success(devices);
    }

    //TODO 更进一步，当设备设为不可用时，帮忙把将要在这里上课的学生调个座位
    @Override
    public Result<?> updateDeviceById(Device device) {
        if(deviceMapper.updateById(device)>0){
            return Result.success();
        }
        else {
            return Result.error("-1","设备更新失败");
        }
    }

    @Override
    public Result<?> addDevice(Device device) {
        if(deviceMapper.insert(device)>0){
            return Result.success();
        }
        else{
            return Result.error("-1","设备插入失败");
        }
    }

    @Override
    public Result<?> addDevice(Room room) {
        int col=room.getRoomCol();
        int row=room.getRoomRow();
        for (int i=1; i<=row; i++){
            for (int j=1; j<=col; j++){
                Device device=new Device(IdUtil.fastSimpleUUID(), room.getRoomId(), i,j,"AVAILABLE");//id生成工具包
                addDevice(device);
            }
        }
        return Result.success();
    }

    @Override
    public Result<?> getDeviceById(String id) {
        Device device=deviceMapper.selectById(id);
        //QueryWrapper
        return Result.success(device);
    }
}
