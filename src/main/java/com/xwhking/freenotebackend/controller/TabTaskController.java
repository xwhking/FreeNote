package com.xwhking.freenotebackend.controller;

import com.xwhking.freenotebackend.common.BaseResponse;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.common.ResultUtils;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.TabTask;
import com.xwhking.freenotebackend.model.request.TabTaskListRequest;
import com.xwhking.freenotebackend.model.request.TabTaskRequest;
import com.xwhking.freenotebackend.service.TabTaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

@RestController
@RequestMapping("/tabTask")
public class TabTaskController {
    @Resource
    private TabTaskService tabTaskService ;

    @PostMapping("/create")
    public BaseResponse<TabTask> createTabTask(@RequestBody TabTaskRequest tabTaskRequest, HttpServletRequest request){
        return ResultUtils.success(tabTaskService.createOne(tabTaskRequest,request));
    }

    @GetMapping("/list")
    public BaseResponse<List<TabTask>> getList(TabTaskListRequest tabTaskListRequest , HttpServletRequest request){
        return ResultUtils.success(tabTaskService.getList(tabTaskListRequest,request));
    }

    @PostMapping("/edit")
    public BaseResponse<TabTask> editTabTask(@RequestBody TabTaskRequest tabTaskRequest,HttpServletRequest request){
        return ResultUtils.success(tabTaskService.edit(tabTaskRequest,request));
    }

    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteTabTask(TabTaskRequest tabTaskRequest,HttpServletRequest request ){
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }
        if(tabTaskRequest == null || tabTaskRequest.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"删除tab的id为空");
        }
        if(tabTaskService.removeById(tabTaskRequest.getId())){
            return ResultUtils.success(true );
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除错误，id不存在或其他原因");
    }
}
