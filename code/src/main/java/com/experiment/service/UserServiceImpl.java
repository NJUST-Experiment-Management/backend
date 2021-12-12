package com.experiment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.experiment.common.Result;
import com.experiment.entity.User;
import com.experiment.mapper.UserMapper;
import com.experiment.utils.TokenUtils;
import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    UserMapper userMapper;

    @Override
    public Result<?> login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getUserId()).eq("password", user.getPassword());
        User res = userMapper.selectOne(queryWrapper);
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        String token = TokenUtils.genToken(res);
        res.setToken(token);
        return Result.success(res);
    }

    //TODO 待测试
    @Override
    public Result<?> importUserByExcel(MultipartFile file) {
        Result<?> result = readUsersByExcel(file);
        if (result.getCode().equals("-1"))
            return result;
        if(result.getData() instanceof List<?> ) {
            for (Object obj : (List<?>) result.getData())
                userMapper.insert((User) obj);
        }
        else{
            return Result.error("-1","未知错误");
        }
        return Result.success();
    }

    @Override
    public Result<?> updateUserById(User user) {
        if(userMapper.updateById(user) > 0)
            return Result.success();
        return Result.error("-1","更新个人信息失败");
    }

    @Override
    public Result<?> getUserById(String userId) {
        return Result.success(userMapper.selectById(userId));
    }

    @Override
    public Result<?> getUserById(List<String> userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().in("USER_ID", userId);
        return Result.success(userMapper.selectList(queryWrapper));
    }

    //TODO 待测试
    @Override
    public Result<?> readUsersByExcel(MultipartFile file) {
        Workbook workbook;
        User user = new User();
        List<User> userList = new ArrayList<>();
        try{
            InputStream inputStream = file.getInputStream();
            if(file.getOriginalFilename().matches(".*(.xls|.XLS)$"))
                workbook = new HSSFWorkbook(inputStream);
            else if(file.getOriginalFilename().matches(".*(.xlsx|.XLSX)$"))
                workbook = new XSSFWorkbook(inputStream);
            else{
                return Result.error("-1", "上传文件类型有误");
            }
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++){
                Row row = sheet.getRow(i);
                user.setUserId(formatter.formatCellValue(row.getCell(1)));
                user.setUserName(formatter.formatCellValue(row.getCell(2)));
                user.setUserPhone(formatter.formatCellValue(row.getCell(3)));
                user.setUserType(formatter.formatCellValue(row.getCell(4)));
                userList.add(user);
            }
        }catch (IOException e){
            e.printStackTrace();
            return Result.error("-1", "上传失败");
        }catch (NullPointerException e){
            e.printStackTrace();
            return Result.error("-1", "文件名不能为空");
        }
        return Result.success(userList);
    }
}
