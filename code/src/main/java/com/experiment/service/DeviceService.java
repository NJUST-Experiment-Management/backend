package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.Device;
import com.experiment.entity.Room;

public interface DeviceService {
    Result<?> getAllDevice(String roomId);

    Result<?> updateDeviceById(Device device);
    Result<?> addDevice(Device device);
    Result<?> addDevice(Room room);
    Result<?> getDeviceById(String id);
}
