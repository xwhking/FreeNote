package com.xwhking.freenotebackend.controller;

import com.google.code.kaptcha.Producer;
import com.xwhking.freenotebackend.Utils.GetRandomCode;
import com.xwhking.freenotebackend.Utils.QQEmailSender;
import com.xwhking.freenotebackend.common.BaseResponse;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.common.ResultUtils;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.User;
import com.xwhking.freenotebackend.model.request.AvatarRequest;
import com.xwhking.freenotebackend.model.request.PasswordRequest;
import com.xwhking.freenotebackend.model.request.UserLoginRequest;
import com.xwhking.freenotebackend.model.request.UserRegisterRequest;
import com.xwhking.freenotebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static com.xwhking.freenotebackend.constant.UserConstant.*;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    private final Producer captchaProducer;

    public UserController(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }
    @GetMapping("/getRegisterCode")
    public BaseResponse<Boolean> getRegisterCode(String mail, HttpServletRequest request){
        String code = GetRandomCode.generateRandomCode(REGISTER_DIGIT);
        request.getSession().setAttribute(REGISTER_CODE,code);
        // TODO 弊端,一个浏览器用一个验证码就可以无限注册; 后期改redis存通过邮箱为键，code为value
        QQEmailSender.SendQQMail(mail,code);
        return ResultUtils.success(true);
    }
    @PostMapping("/register")
    public BaseResponse<Boolean> userRegister(@RequestBody UserRegisterRequest userRegisterRequest, HttpServletRequest request){
       return ResultUtils.success(userService.register(userRegisterRequest,request));
    }

    @PostMapping("/login")
    public BaseResponse<UserDTO> userLogin(@RequestBody UserLoginRequest userLoginRequest,HttpServletRequest request){
        log.info((String) request.getSession().getAttribute(LOGIN_CODE));
        return ResultUtils.success(userService.login(userLoginRequest,request));
    }

    // TODO 把验证码存到redis中，直接用session用起来比较烦
    @GetMapping("/imgcode")
    public BaseResponse<String> getImgCode(HttpServletRequest request) throws IOException {
        // TODO 设置同一个账户限流
        // 生成验证码文本
        String captchaText = captchaProducer.createText();
        // 存储验证码文本到会话中，以便稍后验证
        request.getSession().setAttribute(LOGIN_CODE,captchaText);
        log.info("当前验证码 : " + captchaText);
        // 生成验证码图片
        BufferedImage captchaImage = captchaProducer.createImage(captchaText);

        // 将验证码图片转换为Base64字符串
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(captchaImage, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        request.getSession().setAttribute("shabi","1234");
        return ResultUtils.success(base64Image);
    }
    @GetMapping("/getCurrentUser")
    public BaseResponse<UserDTO> getCurrentUser(HttpServletRequest request){
        return ResultUtils.success(userService.getSafeUser(request));
    }

    @PostMapping("/changeAvatar")
    public BaseResponse<Boolean> changeAvatar(@RequestBody AvatarRequest base64Value, HttpServletRequest request){
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if (loginUser==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if(base64Value == null ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(loginUser.getId());
        String url = base64Value.getBase64Url();
        user.setUserAvatar(url);
        return ResultUtils.success(userService.updateById(user));
    }

    @PostMapping("/changePassword")
    public BaseResponse<Boolean> changePassword(@RequestBody PasswordRequest passwordRequest, HttpServletRequest request ){
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if (loginUser==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if(passwordRequest == null || passwordRequest.getOldPassword() == null || passwordRequest.getNewPassword() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请输入新旧密码");
        }
        User user = userService.getById(loginUser.getId());
        if(!user.getUserPassword().equals(passwordRequest.getOldPassword())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"原密码错误");
        }
        if(passwordRequest.getNewPassword().equals(passwordRequest.getOldPassword())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次密码相同");
        }
        user.setUserPassword(passwordRequest.getNewPassword());
        return ResultUtils.success(userService.updateById(user));
    }

}
