package com.xwhking.freenotebackend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.TabTask;
import com.xwhking.freenotebackend.model.entity.Tabs;
import com.xwhking.freenotebackend.mapper.TabsMapper;
import com.xwhking.freenotebackend.model.request.TabListRequest;
import com.xwhking.freenotebackend.model.request.TabRequest;
import com.xwhking.freenotebackend.service.TabsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

/**
 * @author 28374
 * @description 针对表【tabs(标签)】的数据库操作Service实现
 * @createDate 2023-08-18 17:25:06
 */
@Service
public class TabsServiceImpl extends ServiceImpl<TabsMapper, Tabs>
        implements TabsService {

    @Override
    public Tabs createOne(TabRequest tabRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }
        // 验证参数
        if(tabRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        String title = tabRequest.getTitle();
        String profile = tabRequest.getTabProfile();
        if(title == null || "".equals(title)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"标题不可为空");
        }
        Tabs tab = new Tabs();
        tab.setTabProfile(profile);
        tab.setTitle(title);
        tab.setUserId(loginUser.getId());
        if(this.save(tab)){
            return tab;
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据创建失败");
    }

    @Override
    public List<Tabs> getList(TabListRequest tabListRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"未登录");
        }
        if(tabListRequest == null ){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        LambdaQueryWrapper<Tabs> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Tabs::getUserId,loginUser.getId());
        if(tabListRequest.getSearchTitle() != null && !"".equals(tabListRequest.getSearchTitle())){
            lambdaQueryWrapper.like(Tabs::getTitle,tabListRequest.getSearchTitle());
        }
        lambdaQueryWrapper.orderByDesc(Tabs::getCreateTime);
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public Tabs edit(TabRequest tabRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"未登录");
        }
        int count = 0;
        if(tabRequest == null || tabRequest.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请输入id");
        }
        Long id = tabRequest.getId();
        String title = tabRequest.getTitle();
        String profile = tabRequest.getTabProfile();
        Tabs tabs = this.getById(id);
        if(title != null && !"".equals(title)){
            tabs.setTitle(title);
            count++ ;
        }
        if(profile!=null && !"".equals(profile)){
            tabs.setTabProfile(profile);
            count++;
        }
        if(count>0){
            this.updateById(tabs);
            return tabs;
        }
        throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
    }
}




