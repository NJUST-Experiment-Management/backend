package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("device")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @TableId("device_id")
    private String deviceId;
    private String roomId;
    private int deviceRow;
    private int deviceCol;
    private String deviceStatus;
}
