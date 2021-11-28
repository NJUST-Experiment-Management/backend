package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("arr_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArrUser {
    @TableId("arr_user_id")
    private String arrUserId;
    private String userId;
    private Date arrangeDate;
    private int arrangeTime;
    private String courseId;
    private String deviceId;
}
