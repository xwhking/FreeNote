package com.xwhking.freenotebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.User;
import com.xwhking.freenotebackend.model.request.UserLoginRequest;
import com.xwhking.freenotebackend.model.request.UserRegisterRequest;

import javax.servlet.http.HttpServletRequest;

/**
* @author 28374
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-08-14 16:48:18
*/
public interface UserService extends IService<User> {
    Boolean register(UserRegisterRequest userRegisterRequest, HttpServletRequest request);
    UserDTO getSafeUser(User user);
    UserDTO getSafeUser(HttpServletRequest request);
    UserDTO login(UserLoginRequest userLoginRequest,HttpServletRequest request);
}
