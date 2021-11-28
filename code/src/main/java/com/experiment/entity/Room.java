package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @TableId("room_id")
    private String roomId;
    private String roomName;
    private String roomKind;
    private int roomRow;
    private int roomCol;
    private String roomStatus;
}
