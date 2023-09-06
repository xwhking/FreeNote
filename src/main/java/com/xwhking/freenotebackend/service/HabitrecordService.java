package com.xwhking.freenotebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwhking.freenotebackend.model.dto.CalendarData;
import com.xwhking.freenotebackend.model.entity.Habitrecord;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 28374
* @description 针对表【habitrecord(习惯打卡表)】的数据库操作Service
* @createDate 2023-08-15 21:07:27
*/
public interface HabitrecordService extends IService<Habitrecord> {

}
