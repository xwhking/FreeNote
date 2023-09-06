package com.xwhking.freenotebackend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.TabTask;
import com.xwhking.freenotebackend.mapper.TabTaskMapper;
import com.xwhking.freenotebackend.model.request.TabTaskListRequest;
import com.xwhking.freenotebackend.model.request.TabTaskRequest;
import com.xwhking.freenotebackend.service.TabTaskService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

/**
* @author 28374
* @description 针对表【tab_task(标签栏下的任务)】的数据库操作Service实现
* @createDate 2023-08-18 17:25:06
*/
@Service
public class TabTaskServiceImpl extends ServiceImpl<TabTaskMapper, TabTask>
    implements TabTaskService{

    @Override
    public TabTask createOne(TabTaskRequest tabTaskRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }
        // 验证参数
        if(tabTaskRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空"   );
        }
        String title = tabTaskRequest.getTitle();
        String profile = tabTaskRequest.getProfile();
        Long tabId = tabTaskRequest.getTabId();
        if(tabId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请输入TabId");
        }
        if(title == null || "".equals(title)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"标题不可为空");
        }
        TabTask tabTask = new TabTask();
        tabTask.setTabId(tabId);
        tabTask.setTabProfile(profile);
        tabTask.setTitle(title);
        if(this.save(tabTask)){
            return tabTask;
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据创建失败");
    }

    @Override
    public List<TabTask> getList(TabTaskListRequest tabTaskListRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"未登录");
        }
        if(tabTaskListRequest == null || tabTaskListRequest.getTabId() == null){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空，请输入正确id");
        }
        LambdaQueryWrapper<TabTask> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TabTask::getTabId,tabTaskListRequest.getTabId());
        if(tabTaskListRequest.getSearchTitle() != null && !"".equals(tabTaskListRequest.getSearchTitle())){
            lambdaQueryWrapper.like(TabTask::getTitle,tabTaskListRequest.getSearchTitle());
        }
        lambdaQueryWrapper.orderByDesc(TabTask::getCreateTime);
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public TabTask edit(TabTaskRequest tabTaskRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"未登录");
        }
        int count = 0;
        if(tabTaskRequest == null || tabTaskRequest.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请输入id");
        }
        Long id = tabTaskRequest.getId();
        String title = tabTaskRequest.getTitle();
        String profile = tabTaskRequest.getProfile();
        Integer isFinish = tabTaskRequest.getIsFinish();
        TabTask tabTask = this.getById(id);
        if(title != null && !"".equals(title)){
            tabTask.setTitle(title);
            count++ ;
        }
        if(profile!=null && !"".equals(profile)){
            tabTask.setTabProfile(profile);
            count++;
        }
        if(isFinish != null ){
            if((isFinish == 0 || isFinish ==1) && !isFinish.equals(tabTask.getIsFinish())){
                tabTask.setIsFinish(isFinish);
                count++;
            }else{
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"完成参数不正确");
            }
        }
        if(count>0){
            this.updateById(tabTask);
            return tabTask;
        }
        throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
    }
}




