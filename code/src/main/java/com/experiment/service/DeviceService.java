package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.Device;
import com.experiment.entity.Room;

import java.util.Date;
import java.util.List;

public interface DeviceService {
    /**
     * <p>通过房间id获取房间内所有的可用机器</p>
     *
     * @param roomId 房间号
     * @return 返回所有的可用设备
     */
    Result<?> getAllDevice(String roomId);

    /**
     * <p>返回指定房间列表的所有可用座位</p>
     *
     * @param roomList 目标房间列表
     * @return List-Device
     */
    Result<?> getAvailableDevice(List<Room> roomList, Date date, Integer time);

    /**
     * <p>通过设备id号更新设备</p>
     *
     * @param device 设备
     * @return 是否成功更新
     */
    Result<?> updateDeviceById(Device device);

    /**
     * <p>增加机位的信息</p>
     *
     * @param device 传入机器的基本信息
     * @return 是否成功增加
     */
    Result<?> addDevice(Device device);

    /**
     * <p>在机房中增加设备，也就是将原本已经有的设备添加到机房当中</p>
     *
     * @param room 所要添加的机房
     * @return 是否添加成功
     */
    Result<?> addDevice(Room room);

    /**
     * <p>通过设备id获取设备</p>
     *
     * @param id 设备id号
     * @return 设备实例本身
     */
    Result<?> getDeviceById(String id);
}
