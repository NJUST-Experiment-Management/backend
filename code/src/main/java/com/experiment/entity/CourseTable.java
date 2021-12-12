package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("course_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseTable {
    private String userId;
    private Date arrangeDate;
    private int arrangeTime;
    private String courseName;
    private String roomName;
    private int deviceRow;
    private int deviceCol;
    private String teacherId;
    private String teacherName;
}
