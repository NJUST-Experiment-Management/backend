package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @TableId("message_id")
    private String messageId;
    private String receiveId;
    private String content;
    private Date sendTime;
    private boolean isRead;
}
