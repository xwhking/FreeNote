package com.xwhking.freenotebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwhking.freenotebackend.model.entity.Todo;
import com.xwhking.freenotebackend.model.request.TodoCreateRequest;

import javax.servlet.http.HttpServletRequest;

/**
* @author 28374
* @description 针对表todo表,意在每天当下要做的事情
* @createDate 2023-08-15 15:42:23
*/
public interface TodoService extends IService<Todo> {
    Todo createOne(TodoCreateRequest todoCreateRequest, HttpServletRequest request);
}
