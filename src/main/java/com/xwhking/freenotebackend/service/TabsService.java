package com.xwhking.freenotebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwhking.freenotebackend.model.entity.TabTask;
import com.xwhking.freenotebackend.model.entity.Tabs;
import com.xwhking.freenotebackend.model.request.TabListRequest;
import com.xwhking.freenotebackend.model.request.TabRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
* @author 28374
* @description 针对表【tabs(标签)】的数据库操作Service
* @createDate 2023-08-18 17:25:06
*/
public interface TabsService extends IService<Tabs> {

    Tabs createOne(TabRequest tabRequest, HttpServletRequest request);

    List<Tabs> getList(TabListRequest tabListRequest, HttpServletRequest request);

    Tabs edit(TabRequest tabRequest, HttpServletRequest request);
}
