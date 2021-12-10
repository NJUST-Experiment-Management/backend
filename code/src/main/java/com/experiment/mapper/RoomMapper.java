package com.experiment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.experiment.entity.ArrRoom;
import com.experiment.entity.Room;

import java.util.List;

public interface RoomMapper extends BaseMapper<Room> {
    Integer getOkRoomNum(ArrRoom arrRoom);
}
