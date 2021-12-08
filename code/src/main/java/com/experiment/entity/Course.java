package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @TableId("course_id")
    private String courseId;
    private String courseName;
    private String courseContent;
    private String teacherId;
    private Date createTime;
    private Boolean isOpening;
}
