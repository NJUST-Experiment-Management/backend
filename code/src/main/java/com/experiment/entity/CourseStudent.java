package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("course_student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseStudent {
    private String courseId;
    private String studentId;
}
