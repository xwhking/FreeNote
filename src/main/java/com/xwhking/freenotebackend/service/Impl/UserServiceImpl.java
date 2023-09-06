package com.xwhking.freenotebackend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwhking.freenotebackend.Utils.MD5Encryption;
import com.xwhking.freenotebackend.Utils.SpecialCharacterValidator;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.User;
import com.xwhking.freenotebackend.mapper.UserMapper;
import com.xwhking.freenotebackend.model.request.UserLoginRequest;
import com.xwhking.freenotebackend.model.request.UserRegisterRequest;
import com.xwhking.freenotebackend.service.UserService;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.xwhking.freenotebackend.constant.UserConstant.*;

/**
 * @author 28374
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-08-14 16:48:18
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    /**
     * 用户注册
     * @param userRegisterRequest
     * @param request
     * @return
     */
    @Override
    public Boolean register(UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
        // 判空
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        // 把变量单独取出来便于验证
        String userName = userRegisterRequest.getUserName();
        String email = userRegisterRequest.getEmail();
        String registerCode = userRegisterRequest.getRegisterCode();
        String userPassword = userRegisterRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userName, email, registerCode, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数有为空的，请检查后再上传");
        }
        // 验证验证码是否正确 从session里面获取验证码与输入的验证码进行对比验证
        String rightCode = (String) request.getSession().getAttribute(REGISTER_CODE);
        if (!registerCode.equals(rightCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"验证码错误");
        }
        //验证邮箱是否存在
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail,email);
        User emailUser = this.getOne(lambdaQueryWrapper);
        if(emailUser!=null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"邮箱已经存在");
        }
        // 校验用户名是否含有特殊字符,用户名长度要大于4 小于50 校验用户名是否存在
        if(userName.length() > 50 || userName.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名长度不正确大于50小于4");
        }
        if(SpecialCharacterValidator.containsSpecialCharacters(userName)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名含有特殊字符");
        }
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,userName);
        User nameUser = this.getOne(lambdaQueryWrapper);
        if(nameUser!=null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名已经存在");
        }
        // 校验密码长度
        if(userPassword.length() < 8 || userPassword.length() > 18 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度小于8或大于18");
        }
        User user = new User();
        user.setEmail(email);
        user.setUserPassword(MD5Encryption.encrypt(MD5_SALT + userPassword));
        user.setUserName(userName);
        Boolean isSave = this.save(user);
        return isSave;
    }

    /**
     * 获取用户脱敏信息
     * @param user
     * @return
     */
    @Override
    public UserDTO getSafeUser(User user) {
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"获取用户信息失败用户为空");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUserName(user.getUserName());
        userDTO.setUserAvatar(user.getUserAvatar());
        userDTO.setUserProfile(user.getUserProfile());
        userDTO.setUserRole(user.getUserRole());
        return userDTO;
    }

    /**
     * 获取用户脱敏信息
     * @param request
     * @return
     */
    @Override
    public UserDTO getSafeUser(HttpServletRequest request) {
        UserDTO userDTO = (UserDTO)request.getSession().getAttribute(CURRENT_USER);
        if(userDTO == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"用户未登录");
        }
        userDTO = this.getSafeUser(this.getById(userDTO.getId()));
        return userDTO;
    }

    /**
     * 用户登录 并且返回用户脱敏信息
     * @param userLoginRequest
     * @param request
     * @return
     */
    @Override
    public UserDTO login(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        String userName = userLoginRequest.getUserName();
        String userPassword = userLoginRequest.getUserPassword();
        String loginCode = userLoginRequest.getLoginCode();
        String email = userLoginRequest.getEmail()  ;
        // 验证验证码
        if(loginCode == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请输入验证码");
        }
        log.info(request.getSession().toString());
        String code = (String)request.getSession().getAttribute(LOGIN_CODE);
        if(!loginCode.equals(code)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"验证码错误");
        }
        // 校验用户名或者邮箱
        if(StringUtils.isAllBlank(userName,email)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名或者邮箱为空");
        }
        if(userPassword == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请输入密码");
        }
        User dbUser = new User();
        if(userName!=null && !"".equals(userName)){
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getUserName,userName);
            dbUser = this.getOne(lambdaQueryWrapper);
            if(dbUser == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名不存在");
            }
            if(!dbUser.getUserPassword().equals(userPassword)){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码错误");
            }
        }
        if(email != null &&  !"".equals(email)){
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getEmail,email);
            dbUser = this.getOne(lambdaQueryWrapper);
            if(dbUser == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"邮箱不存在");
            }
            if(!dbUser.getUserPassword().equals(userPassword)){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码错误");
            }
        }
        request.getSession().setAttribute(CURRENT_USER,this.getSafeUser(dbUser));
        return this.getSafeUser(dbUser);
    }
}




