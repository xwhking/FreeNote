package com.xwhking.freenotebackend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.Todo;
import com.xwhking.freenotebackend.mapper.TodoMapper;
import com.xwhking.freenotebackend.model.request.TodoCreateRequest;
import com.xwhking.freenotebackend.service.TodoService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

/**
* @author 28374
* @description 针对todo表,意在每天当下要做的事情
* @createDate 2023-08-15 15:42:23
*/
@Service
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo>
    implements TodoService{

    /**
     * 创建一个TODO任务
     *
     * @param todoCreateRequest
     * @param request
     * @return
     */
    @Override
    public Todo createOne(TodoCreateRequest todoCreateRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"请先登录");
        }
        if(todoCreateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        // 判断参数是否为空
        String title = todoCreateRequest.getTitle();
        String profile = todoCreateRequest.getTodoProfile();
        if(title == null ||  "".equals(title)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"标题为空");
        }
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setTodoProfile(profile);
        todo.setUserId(loginUser.getId());
        boolean isSave = this.save(todo);
        if(isSave){
            return todo;
        }else{
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"创建失败");
        }
    }
}




