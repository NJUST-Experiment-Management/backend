package com.experiment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.experiment.entity.ArrRoom;
import com.experiment.entity.Room;

import java.util.List;

public interface RoomMapper extends BaseMapper<Room> {
    List<Room> getRooms();
    Integer getOkRoomNum(ArrRoom arrRoom);
}
