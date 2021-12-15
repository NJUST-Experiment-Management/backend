package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("arr_room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArrRoom {
    @TableId("arr_room_id")
    private String arrRoomId;
    private String roomId;
    private String courseId;
    private Date arrangeDate;
    private int arrangeTime;
    private boolean isShareable;
}
