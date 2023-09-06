package com.xwhking.freenotebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwhking.freenotebackend.model.entity.Target;
import com.xwhking.freenotebackend.model.request.TargetCreateRequest;
import com.xwhking.freenotebackend.model.request.TargetEditRequest;
import com.xwhking.freenotebackend.model.request.TargetListRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
* @author 28374
* @description 针对表【target(目标规划)】的数据库操作Service
* @createDate 2023-08-18 22:24:40
*/
public interface TargetService extends IService<Target> {

    List<Target> getList(TargetListRequest targetListRequest, HttpServletRequest request);

    Target create(TargetCreateRequest targetCreateRequest, HttpServletRequest request);

    Target edit(TargetEditRequest targetEditRequest, HttpServletRequest request);
}
