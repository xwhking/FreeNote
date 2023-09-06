package com.xwhking.freenotebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwhking.freenotebackend.model.dto.CalendarData;
import com.xwhking.freenotebackend.model.dto.HabitDetailDTO;
import com.xwhking.freenotebackend.model.entity.Habit;
import com.xwhking.freenotebackend.model.request.HabitCreateRequest;
import com.xwhking.freenotebackend.model.request.HabitEditRequest;
import com.xwhking.freenotebackend.model.request.HabitIdRequest;
import com.xwhking.freenotebackend.model.request.HabitListRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
* @author 28374
* @description 针对表【habit(习惯表)】的数据库操作Service
* @createDate 2023-08-15 21:07:27
*/
public interface HabitService extends IService<Habit> {

    Habit createOne(HabitCreateRequest habitCreateRequest, HttpServletRequest request);

    Habit edit(HabitEditRequest habitEditRequest, HttpServletRequest request);

    List<Habit> getList(HabitListRequest habitListRequest, HttpServletRequest request);

    HabitDetailDTO getDetail(HabitIdRequest habitIdRequest, HttpServletRequest request);

    List<CalendarData> summary(HttpServletRequest request );
}
