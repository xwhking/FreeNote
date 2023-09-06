package com.xwhking.freenotebackend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwhking.freenotebackend.Utils.MyDateUtils;
import com.xwhking.freenotebackend.common.BaseResponse;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.CalendarData;
import com.xwhking.freenotebackend.model.dto.HabitDetailDTO;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.Habit;
import com.xwhking.freenotebackend.mapper.HabitMapper;
import com.xwhking.freenotebackend.model.entity.Habitrecord;
import com.xwhking.freenotebackend.model.request.HabitCreateRequest;
import com.xwhking.freenotebackend.model.request.HabitEditRequest;
import com.xwhking.freenotebackend.model.request.HabitIdRequest;
import com.xwhking.freenotebackend.model.request.HabitListRequest;
import com.xwhking.freenotebackend.service.HabitService;
import com.xwhking.freenotebackend.service.HabitrecordService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

/**
* @author 28374
* @description 针对表【habit(习惯表)】的数据库操作Service实现
* @createDate 2023-08-15 21:07:27
*/
@Service
public class HabitServiceImpl extends ServiceImpl<HabitMapper, Habit>
    implements HabitService{
    @Resource
    HabitrecordService habitrecordService;
    /**
     * 创建习惯
     * @param habitCreateRequest
     * @param request
     * @return
     */
    @Override
    public Habit createOne(HabitCreateRequest habitCreateRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 参数验证 title不能为空 endTime距创建时间不能小于21天
        if(habitCreateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        String title = habitCreateRequest.getTitle();
        if(title == null || "".equals(title)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"标题不能为空");
        }
        LocalDate endTime = habitCreateRequest.getEndTime();
        Long dif = MyDateUtils.getBetweenDays(LocalDate.now(),endTime);
        if(dif < 20){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"习惯的养成不能小于21天哦");
        }
        Habit habit = new Habit();
        habit.setTitle(title);
        habit.setHabitProfile(habitCreateRequest.getHabitProfile());
        habit.setEndTime(endTime);
        habit.setUserId(loginUser.getId());
        boolean isSave = this.save(habit);
        if(isSave){
            return habit;
        }else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"创建失败");
        }
    }

    @Override
    public Habit edit(HabitEditRequest habitEditRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 参数验证 title,profile不能为空即修改 endTime距创建时间不能小于21天
        int count  = 0;// 数据更新个数
        if(habitEditRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        Habit habit = this.getById(habitEditRequest.getId());
        if(habit == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"习惯不存在");
        }
        String title = habitEditRequest.getTitle();
        if(title != null && !"".equals(title) && !title.equals(habit.getTitle())){
            habit.setTitle(title);
            count++;
        }
        LocalDate endTime = habitEditRequest.getEndTime();
        if(endTime != null && !MyDateUtils.getFormatDate(endTime).equals(MyDateUtils.getFormatDate(habit.getEndTime()))){
            Long dif = MyDateUtils.getBetweenDays(LocalDate.now(),endTime);
            if(dif < 20){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"习惯的养成不能小于21天哦");
            }
            habit.setEndTime(endTime);
            count++;
        }
        String profile = habitEditRequest.getHabitProfile();
        if(profile != null && !"".equals(profile) && !profile.equals(habit.getHabitProfile())){
            habit.setHabitProfile(profile);
            count++;
        }
        if(count>0){
            boolean isUpdate = this.updateById(habit);
            if(isUpdate){
                return habit;
            }else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新失败");
            }
        }else{
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"未更新参数");
        }
    }

    @Override
    public List<Habit> getList(HabitListRequest habitListRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        LambdaQueryWrapper<Habit> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Habit::getUserId,loginUser.getId());
        // 判断搜索词是否为空
        if(habitListRequest == null || habitListRequest.getSearchText() == null || habitListRequest.getSearchText().equals("")){
            lambdaQueryWrapper.orderByDesc(Habit::getCreateTime);
            return this.list(lambdaQueryWrapper);
        }
        String searchText = habitListRequest.getSearchText();
        if(searchText!=null && !"".equals(searchText)){
            lambdaQueryWrapper.like(Habit::getTitle,searchText);
        }
        lambdaQueryWrapper.orderByDesc(Habit::getCreateTime);
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public HabitDetailDTO getDetail(HabitIdRequest habitIdRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 获取到Habit 然后对响应数据进行计算
        if(habitIdRequest == null && habitIdRequest.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        Habit habit = this.getById(habitIdRequest.getId());
        if(habit == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"未找到习惯");
        }
        // 获取开始时间和结束时间计算总需要打卡值
        LocalDate startTime = habit.getCreateTime();
        LocalDate endTime = habit.getEndTime();
        long totalRecord = MyDateUtils.getBetweenDays(startTime,endTime);
        // 获取已经打卡数，就是在record表中查询habitid等于这条habitid的记录总数
        LambdaQueryWrapper<Habitrecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Habitrecord::getHabitId,habit.getId());
        List<Habitrecord> habitrecords = habitrecordService.list(lambdaQueryWrapper );
        long finishRecord =  habitrecords.size();
        // 计算已经需要打卡数，计算未打卡数
        long needRecord = MyDateUtils.getBetweenDays(startTime,LocalDate.now());
        long failRecord = needRecord - finishRecord;
        // 计算剩余打卡数
        long surplusRecord = totalRecord - needRecord;
        // 生成日历数据
        List<CalendarData> calendarDataList = habitrecords.stream().map((item) -> {
            CalendarData calendarData = new CalendarData();
            calendarData.setDate(MyDateUtils.getFormatDate(item.getCreateTime()));
            calendarData.setCount(1);
            return calendarData;
        }).collect(Collectors.toList());
        HabitDetailDTO habitDetailDTO = new HabitDetailDTO();
        habitDetailDTO.setTargetHabit(habit);
        habitDetailDTO.setFinishRecord(finishRecord);
        habitDetailDTO.setFailRecord(failRecord);
        habitDetailDTO.setSurplusRecord(surplusRecord);
        habitDetailDTO.setTotalRecord(totalRecord);
        habitDetailDTO.setCalendarDataList(calendarDataList);
        LambdaQueryWrapper<Habitrecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Habitrecord::getHabitId,habit.getId());
        queryWrapper.orderByDesc(Habitrecord::getCreateTime);
        habitDetailDTO.setHabitrecords(habitrecordService.list(queryWrapper));
        return habitDetailDTO;
    }

    /**
     * 获取总的习惯打卡情况
     * @param request
     * @return
     */
    // todo 这个方法的数据可以存到Redis,避免多次取数据
    @Override
    public List<CalendarData> summary(HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO)request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        LambdaQueryWrapper<Habit> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Habit::getUserId,loginUser.getId());
        // 查询个人所有习惯
        List<Long> habitIdList = this.list(lambdaQueryWrapper).stream().map(Habit::getId).collect(Collectors.toList());
        LambdaQueryWrapper<Habitrecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Habitrecord::getHabitId,habitIdList );
        List<Habitrecord> habitrecordList = habitrecordService.list(queryWrapper);
        // 首先获取到所有的每日日期
        Set<String> dates = new HashSet<>();
        for(Habitrecord item : habitrecordList){
            String date = MyDateUtils.getFormatDate(item.getCreateTime());
            dates.add(date);
        }
        List<CalendarData> calendarDataList = new ArrayList<>();
        // 对打卡次数进行统计
        for(String date : dates){
            int count = 0;
            for(Habitrecord item: habitrecordList){
                if(date.equals(MyDateUtils.getFormatDate(item.getCreateTime()))){
                    count++;
                }
            }
            CalendarData calendarData = new CalendarData();
            calendarData.setDate(date);
            calendarData.setCount(count);
            calendarDataList.add(calendarData);
        }
        return calendarDataList;
    }
}




