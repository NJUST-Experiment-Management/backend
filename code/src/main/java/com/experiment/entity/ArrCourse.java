package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("ArrCourse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArrCourse {
    private String arrUserId;
    private String userId;
    private Date arrangeDate;
    private int arrangeTime;
    private String courseId;
    private String deviceId;
    private String courseName;
    private String courseContent;
    private String teacherId;
    private Date createTime;
}
