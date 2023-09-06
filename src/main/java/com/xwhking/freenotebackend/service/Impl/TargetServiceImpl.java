package com.xwhking.freenotebackend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.Target;
import com.xwhking.freenotebackend.mapper.TargetMapper;
import com.xwhking.freenotebackend.model.request.TargetCreateRequest;
import com.xwhking.freenotebackend.model.request.TargetEditRequest;
import com.xwhking.freenotebackend.model.request.TargetListRequest;
import com.xwhking.freenotebackend.service.TargetService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

/**
* @author 28374
* @description 针对表【target(目标规划)】的数据库操作Service实现
* @createDate 2023-08-18 22:24:40
*/
@Service
public class TargetServiceImpl extends ServiceImpl<TargetMapper, Target>
    implements TargetService{

    /**
     * 列表获取 后期可以配置分页，模糊查询标题和内容，查询是否完成内容
     * @param targetListRequest
     * @param request
     * @return
     */
    @Override
    public List<Target> getList(TargetListRequest targetListRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"未登录");
        }
        // 进行条件查询
        LambdaQueryWrapper<Target> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtils.isNotEmpty(targetListRequest)){
            String searchTitle = targetListRequest.getSearchTitle();
            String searchContent = targetListRequest.getSearchContent();
            Integer isFinish = targetListRequest.getIsFinish();
            if(searchContent != null && !"".equals(searchContent)){
                lambdaQueryWrapper.like(Target::getContent,searchContent);
            }
            if(searchTitle!=null && !"".equals(searchTitle)){
                lambdaQueryWrapper.like(Target::getTitle,searchTitle);
            }
            if(isFinish != null){
                if(isFinish == 1 || isFinish == 0){
                    lambdaQueryWrapper.eq(Target::getIsFinish,isFinish);
                }
            }
        }
        lambdaQueryWrapper.eq(Target::getUserId,loginUser.getId());
        lambdaQueryWrapper.orderByDesc(Target::getCreateTime);
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public Target create(TargetCreateRequest targetCreateRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"未登录");
        }
        if(targetCreateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误");
        }
        String title = targetCreateRequest.getTitle();
        String content = targetCreateRequest.getContent();
        LocalDate endTime = targetCreateRequest.getEndTime();
        if(StringUtils.isAnyEmpty(title,content) || StringUtils.isAnyBlank(title,content)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"内容和标题不可为空");
        }
        Target target = new Target();
        target.setTitle(title);
        target.setUserId(loginUser.getId());
        target.setContent(content);
        target.setEndTime(endTime);
        this.save(target);
        return target;
    }

    @Override
    public Target edit(TargetEditRequest targetEditRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"未登录");
        }
        if(targetEditRequest == null || targetEditRequest.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        int count = 0;
        Target target = this.getById(targetEditRequest.getId()) ;
        String title = targetEditRequest.getTitle();
        String content = targetEditRequest.getContent();
        LocalDate endTime = targetEditRequest.getEndTime();
        if(title != null && !"".equals(title)){
            target.setTitle(title);
            count++;
        }
        if(content != null && !"".equals(content)){
            target.setContent(content);
            count++;
        }
        if(endTime != null){
            target.setEndTime(endTime);
            count++;
        }
        Integer isFinish = targetEditRequest.getIsFinish();
        if(isFinish!=null){
            if(isFinish != 0 && isFinish!=1){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"是否完成只有两个状态0，1");
            }
            if(!isFinish.equals(target.getIsFinish()))
            {
                target.setIsFinish(isFinish);
                count++;
            }
        }
        if(count>0){
            this.updateById(target);
            return target;
        }
        throw new BusinessException(ErrorCode.PARAMS_ERROR,"数据未修改");
    }
}




