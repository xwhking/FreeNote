package com.xwhking.freenotebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwhking.freenotebackend.model.entity.TabTask;
import com.xwhking.freenotebackend.model.request.TabTaskListRequest;
import com.xwhking.freenotebackend.model.request.TabTaskRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
* @author 28374
* @description 针对表【tab_task(标签栏下的任务)】的数据库操作Service
* @createDate 2023-08-18 17:25:06
*/
public interface TabTaskService extends IService<TabTask> {

    TabTask createOne(TabTaskRequest tabTaskRequest, HttpServletRequest request);

    List<TabTask> getList(TabTaskListRequest tabTaskListRequest, HttpServletRequest request);

    TabTask edit(TabTaskRequest tabTaskRequest, HttpServletRequest request);
}
