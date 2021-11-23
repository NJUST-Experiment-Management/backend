package com.experiment.controller;

import com.auth0.jwt.JWT;
import com.experiment.entity.User;
import com.experiment.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BaseController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HttpServletRequest request;

    public User getUser() {
        String token = request.getHeader("token");
        String aud = JWT.decode(token).getAudience().get(0);
        return userMapper.selectById(aud);
    }

    public String getUserId(){
        String token = request.getHeader("token");
        return JWT.decode(token).getAudience().get(0);
    }
}
