package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("arr_course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArrCourse {
    private String courseId;
    private String courseName;
    private Date arrangeDate;
    private int arrangeTime;
    private String roomId;
    private String roomName;
}
