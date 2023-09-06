package com.xwhking.freenotebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xwhking.freenotebackend.Utils.MyDateUtils;
import com.xwhking.freenotebackend.common.BaseResponse;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.common.ResultUtils;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.mapper.TodoMapper;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.Todo;
import com.xwhking.freenotebackend.model.request.TodoEditRequest;
import com.xwhking.freenotebackend.model.request.TodoIdRequest;
import com.xwhking.freenotebackend.model.request.TodoCreateRequest;
import com.xwhking.freenotebackend.model.request.TodoListRequest;
import com.xwhking.freenotebackend.service.TodoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Resource
    private TodoService todoService;
    @Resource
    private TodoMapper todoMapper;

    /**
     * 创建TODO
     *
     * @param todoCreateRequest
     * @param request
     * @return
     */
    @PostMapping("/create")
    public BaseResponse<Todo> createTodo(@RequestBody TodoCreateRequest todoCreateRequest, HttpServletRequest request) {
        return ResultUtils.success(todoService.createOne(todoCreateRequest, request));
    }


    /**
     * 切换TODO完成未完成状态  0 - 未完成 ， 1 - 完成
     *
     * @param request
     * @return
     */
    @PostMapping("/changeStatus")
    public BaseResponse<Boolean> changeStatus(@RequestBody TodoIdRequest todoIdRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }
        if (todoIdRequest == null || todoIdRequest.getTodoId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long todoId = todoIdRequest.getTodoId();
        Todo todo = todoService.getById(todoId);
        Integer status = todo.getIsFinish();
        if (status == 0) {
            status = 1;
        } else if (status == 1) {
            status = 0;
        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "非法TODO状态");
        }
        todo.setIsFinish(status);
        return ResultUtils.success(todoService.updateById(todo));
    }


    /**
     * 获取todo列表
     * 可以获取当前用户完成的未完成的所有的，根据传递的参数确定返回数据，实施了接口复用
     *
     * @param todoListRequest
     * @param request
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<Todo>> getTodoList(TodoListRequest todoListRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 取出查询完成还是未完成
        if (todoListRequest == null || todoListRequest.getIsFinish() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Integer isFinish = todoListRequest.getIsFinish();
        LambdaQueryWrapper<Todo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Todo::getUserId, loginUser.getId());
        lambdaQueryWrapper.orderByDesc(Todo::getCreateTime);
        // 如果是-1 返回所有todo
        // TODO 优化这段代码
        List<Todo> resultList = new ArrayList<>();
        if (isFinish == -1) {
            String searchText = todoListRequest.getSearchText();
            if (searchText != null && !"".equals(searchText)) {
                lambdaQueryWrapper.like(Todo::getTitle, searchText);
            }
            resultList = todoService.list(lambdaQueryWrapper).stream().filter(item ->
                    !MyDateUtils.getFormatDate(item.getCreateTime()).equals(MyDateUtils.getFormatDate(LocalDate.now()))
            ).collect(Collectors.toList());
            String date = todoListRequest.getDate();
            if (date != null && !"".equals(date)) {
                resultList = resultList.stream().filter(item ->
                        date.equals(MyDateUtils.getFormatDate(item.getCreateTime()))
                        ).collect(Collectors.toList());
            }
            return ResultUtils.success(resultList);
        }
        // 1 或 0 都可以去数据库区分
        lambdaQueryWrapper.eq(Todo::getIsFinish, isFinish);
        String searchText = todoListRequest.getSearchText();
        if (searchText != null && !"".equals(searchText)) {
            lambdaQueryWrapper.like(Todo::getTitle, searchText);
        }
        resultList = todoService.list(lambdaQueryWrapper).stream().filter(item ->
                !MyDateUtils.getFormatDate(item.getCreateTime()).equals(MyDateUtils.getFormatDate(LocalDate.now()))
        ).collect(Collectors.toList());
        return ResultUtils.success(resultList);
    }

    /**
     * 编辑todo
     *
     * @param todoEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Todo> editTodo(@RequestBody TodoEditRequest todoEditRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 对数据进行简单验证
        if (todoEditRequest == null || todoEditRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "todoId为空");
        }
        Todo origin = todoService.getById(todoEditRequest.getId());
        if (todoEditRequest.getTitle() == null || "".equals(todoEditRequest.getTitle())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题不可为空");
        }
        origin.setTodoProfile(todoEditRequest.getProfile());
        origin.setTitle(todoEditRequest.getTitle());
        if (todoService.updateById(origin)) {
            return ResultUtils.success(origin);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改失败");
        }
    }

    /**
     * 删除todo
     *
     * @param todoIdRequest
     * @param request
     * @return
     */
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteTodo(TodoIdRequest todoIdRequest, HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 根据id删除todo记录
        if (todoIdRequest == null || todoIdRequest.getTodoId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(todoService.removeById(todoIdRequest.getTodoId()));
    }

    @GetMapping("/getTodayList")
    public BaseResponse<List<Todo>> getTodayList(HttpServletRequest request) {
        // 判断是否登录
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(CURRENT_USER);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }
        String date = MyDateUtils.getFormatDate(LocalDate.now());
        Long userId = loginUser.getId();
        return ResultUtils.success(todoMapper.getTodayList(date, userId));
    }
}
