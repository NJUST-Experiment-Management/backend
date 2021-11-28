package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("team")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Term {
    private String termName;
    private Date beginTime;
}
