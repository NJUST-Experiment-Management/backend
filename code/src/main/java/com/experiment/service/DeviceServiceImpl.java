package com.experiment.service;

import cn.hutool.core.util.IdUtil;
import com.experiment.common.Result;
import com.experiment.entity.Device;
import com.experiment.entity.Room;
import com.experiment.mapper.DeviceMapper;

import javax.annotation.Resource;

public class DeviceServiceImpl implements DeviceService{
    @Resource
    DeviceMapper deviceMapper;

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
