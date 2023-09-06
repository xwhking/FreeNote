package com.xwhking.freenotebackend.controller;

import com.xwhking.freenotebackend.common.BaseResponse;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.common.ResultUtils;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.Target;
import com.xwhking.freenotebackend.model.request.TargetCreateRequest;
import com.xwhking.freenotebackend.model.request.TargetEditRequest;
import com.xwhking.freenotebackend.model.request.TargetListRequest;
import com.xwhking.freenotebackend.service.TargetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

@RestController
@RequestMapping("/target")
public class TargetController {
    @Resource
    private TargetService targetService;

    @GetMapping("/list")
    public BaseResponse<List<Target>> getTargetList(TargetListRequest targetListRequest, HttpServletRequest request ){
        return ResultUtils.success(targetService.getList(targetListRequest,request));
    }
    @GetMapping("/getOne")
    public BaseResponse<Target> getOne(TargetEditRequest targetEditRequest, HttpServletRequest request){
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"未登录");
        }
        if(targetEditRequest == null || targetEditRequest.getId() == null ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        Target target = targetService.getById(targetEditRequest.getId());
        if(target == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"id不存在");
        }
        return ResultUtils.success(target   );
    }
    @PostMapping("/create")
    public BaseResponse<Target> createTarget(@RequestBody TargetCreateRequest targetCreateRequest, HttpServletRequest request){
        return ResultUtils.success(targetService.create(targetCreateRequest,request));
    }

    @PostMapping("/edit")
    public BaseResponse<Target> editTarget(@RequestBody TargetEditRequest targetEditRequest, HttpServletRequest request){
        return ResultUtils.success(targetService.edit(targetEditRequest,request));
    }
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteTarget(TargetEditRequest targetEditRequest,HttpServletRequest request){
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"未登录");
        }
        if(targetEditRequest== null || targetEditRequest.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(targetService.removeById(targetEditRequest.getId())){
            return ResultUtils.success(true );
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
    }
}

