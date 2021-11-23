package com.experiment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@TableName("lab_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId("user_id")
    private String userId;
    private String password;
    @TableField("user_name")
    private String userName;
    @TableField("user_phone")
    private String userPhone;
    @TableField("user_type")
    private String userType;
}
