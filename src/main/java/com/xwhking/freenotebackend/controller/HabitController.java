package com.xwhking.freenotebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xwhking.freenotebackend.Utils.MyDateUtils;
import com.xwhking.freenotebackend.common.BaseResponse;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.common.ResultUtils;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.mapper.HabitrecordMapper;
import com.xwhking.freenotebackend.model.dto.CalendarData;
import com.xwhking.freenotebackend.model.dto.HabitDetailDTO;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.Habit;
import com.xwhking.freenotebackend.model.entity.Habitrecord;
import com.xwhking.freenotebackend.model.request.*;
import com.xwhking.freenotebackend.service.HabitService;
import com.xwhking.freenotebackend.service.HabitrecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

@RestController
@RequestMapping("/habit")
public class HabitController {
    @Resource
    private HabitService habitService;
    @Resource
    private HabitrecordService habitrecordService;
    @Resource
    private HabitrecordMapper habitrecordMapper;

    /**
     * 创建习惯
     * @param habitCreateRequest
     * @param request
     * @return
     */
    @PostMapping("/create")
    public BaseResponse<Habit> createHabit(@RequestBody HabitCreateRequest habitCreateRequest, HttpServletRequest request){
        return ResultUtils.success(habitService.createOne(habitCreateRequest,request));
    }

    /**
     * 编辑习惯，没有输入或者为空则不更改
     * @param habitEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Habit> editHabit(@RequestBody HabitEditRequest habitEditRequest,HttpServletRequest request){
        return ResultUtils.success(habitService.edit(habitEditRequest,request));
    }

    /**
     * 获取习惯列表 后期可以配置分页，可以按title搜索
     * @param habitListRequest
     * @param request
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<Habit>> getListHabit(HabitListRequest habitListRequest , HttpServletRequest request){
        return ResultUtils.success(habitService.getList(habitListRequest,request));
    }

    /**
     * 获取某个习惯的详情列表返回封装类，封装类里面有关于此习惯的总打卡次数，剩余次数，未打卡次数，打卡日期列表（[{日期,次数},]）便于前端的日期图
     * @param habitIdRequest
     * @param request
     * @return
     */
    @GetMapping("/detail")
    public BaseResponse<HabitDetailDTO> getHabitDetail(HabitIdRequest habitIdRequest, HttpServletRequest request){
        return ResultUtils.success(habitService.getDetail(habitIdRequest,request ));
    }

    /**
     * 习惯打卡接口，如果打卡今天过了，就不能打卡了，如果没有那就可以打卡
     * @param habitClockInRequest
     * @param request
     * @return
     */
    @GetMapping("/clockIn")
    public BaseResponse<Boolean> clockIn(HabitClockInRequest habitClockInRequest , HttpServletRequest request ){
        //判断是否登录
        UserDTO loginUser = (UserDTO)request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if(habitClockInRequest == null && habitClockInRequest.getHabitId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        // 检查今天是否打卡
        Habitrecord habitrecord = habitrecordMapper.getRecord(MyDateUtils.getFormatDate(LocalDate.now()), habitClockInRequest.getHabitId());
        if(habitrecord != null ){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"已经打卡过了");
        }
        habitrecord = new Habitrecord();
        habitrecord.setHabitId(habitClockInRequest.getHabitId());
        habitrecord.setHabitProfile(habitClockInRequest.getProfile());
        boolean isSave = habitrecordService.save(habitrecord);
        if(isSave){
            return ResultUtils.success(true);
        }else{
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取个人的总的打卡习惯
     * @param request
     * @return
     */
    @GetMapping("/summary")
    public BaseResponse<List<CalendarData>> summaryHabit(HttpServletRequest request){
        return ResultUtils.success(habitService.summary(request));
    }
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> delete(HabitIdRequest habitIdRequest,HttpServletRequest request){
        //判断是否登录
        UserDTO loginUser = (UserDTO)request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if(habitIdRequest == null || habitIdRequest.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请输入Id");
        }
        if(habitService.removeById(habitIdRequest.getId())){
            LambdaQueryWrapper<Habitrecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Habitrecord::getHabitId,habitIdRequest.getId());
            habitrecordService.remove(queryWrapper);
            return ResultUtils.success(true );
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败，id不存在，获其他问题");
    }
}
