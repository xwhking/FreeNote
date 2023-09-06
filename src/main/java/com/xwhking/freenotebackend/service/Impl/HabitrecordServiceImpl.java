package com.xwhking.freenotebackend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwhking.freenotebackend.Utils.MyDateUtils;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.CalendarData;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.Habit;
import com.xwhking.freenotebackend.model.entity.Habitrecord;
import com.xwhking.freenotebackend.mapper.HabitrecordMapper;
import com.xwhking.freenotebackend.service.HabitService;
import com.xwhking.freenotebackend.service.HabitrecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

/**
* @author 28374
* @description 针对表【habitrecord(习惯打卡表)】的数据库操作Service实现
* @createDate 2023-08-15 21:07:27
*/
@Service
public class HabitrecordServiceImpl extends ServiceImpl<HabitrecordMapper, Habitrecord>
    implements HabitrecordService{

}




