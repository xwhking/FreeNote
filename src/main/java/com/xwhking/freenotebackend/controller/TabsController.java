package com.xwhking.freenotebackend.controller;

import com.xwhking.freenotebackend.common.BaseResponse;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.common.ResultUtils;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.Tabs;
import com.xwhking.freenotebackend.model.request.TabListRequest;
import com.xwhking.freenotebackend.model.request.TabRequest;
import com.xwhking.freenotebackend.service.TabsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

@RestController
@RequestMapping("/tab")
public class TabsController {
    @Resource
    TabsService tabsService;

    @PostMapping("/create")
    public BaseResponse<Tabs> createTab(@RequestBody TabRequest tabRequest, HttpServletRequest request) {
        return ResultUtils.success(tabsService.createOne(tabRequest, request));
    }

    @GetMapping("/list")
    public BaseResponse<List<Tabs>> getList(TabListRequest tabListRequest, HttpServletRequest request) {
        return ResultUtils.success(tabsService.getList(tabListRequest, request));
    }

    @PostMapping("/edit")
    public BaseResponse<Tabs> editTab(@RequestBody TabRequest tabRequest, HttpServletRequest request) {
        return ResultUtils.success(tabsService.edit(tabRequest, request));
    }

    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteTab(TabRequest tabRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }
        if(tabRequest == null || tabRequest.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"删除tab的id为空");
        }
        if(tabsService.removeById(tabRequest.getId())){
            return ResultUtils.success(true );
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除错误，id不存在或其他原因");
    }


}
