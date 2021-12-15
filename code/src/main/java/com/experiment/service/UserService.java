package com.experiment.service;

import com.experiment.common.Result;
import com.experiment.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author ???
 * <p>该接口提供一系列与人员增删查改相关的操作</p>
 */
public interface UserService {
    Result<?> login(User user);

    /**
     * <p>使用 Excel导入人员信息</p>
     *
     * @param file，一个 excel文档
     * @return Result，表示成功或失败，失败则附有原因说明
     */
    Result<?> importUserByExcel(MultipartFile file);

    /**
     * <p>获取 Excel文档内的人员信息</p>
     *
     * @param file，一个 excel文档
     * @return Result，excel内含有的人员列表，即List&lt;User&gt;，或表示失败信息
     */
    Result<?> readUsersByExcel(MultipartFile file);

    /**
     * <p>更新用户信息</p>
     *
     * <p>根据用户名更新用户信息，可更新的信息包括密码、用户类型、用户联系方式三项</p>
     *
     * @param user，包含需要最新的人员信息
     * @return Result，表示成功或失败
     */
    Result<?> updateUserById(User user);

    /**
     * <p>根据用户的学号、工号唯一确定一个用户</p>
     *
     * @param userId 用户的学号、工号
     * @return Result，包含User
     */
    Result<?> getUserById(String userId);

    /**
     * <p>根据用户的学号、工号，获取一群用户的具体信息</p>
     *
     * @param userId 用户学号、工号的列表
     * @return Result，包含User的列表
     */
    Result<?> getUserById(List<String> userId);

    /**
     * <p>获取某身份的所有人员信息</p>
     *
     * @param userType 人员种类
     * @return Result，成功与否
     */
    Result<?> getUserByType(String userType);

}
