package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.Device;
import com.experiment.entity.Room;

import java.util.Date;
import java.util.List;

public interface DeviceService {
    Result<?> getAllDevice(String roomId);

    /**
     * <p>返回指定房间列表的所有可用座位</p>
     *
     * @param roomList 目标房间列表
     * @return List-Device
     */
    Result<?> getAvailableDevice(List<Room> roomList, Date date, Integer time);

    Result<?> updateDeviceById(Device device);
    Result<?> addDevice(Device device);
    Result<?> addDevice(Room room);
    Result<?> getDeviceById(String id);
}
