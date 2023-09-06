package com.xwhking.freenotebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwhking.freenotebackend.model.entity.Todo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
* @author 28374
* @description 针对表【todo(todo表,意在每天当下要做的事情)】的数据库操作Mapper
* @createDate 2023-08-15 15:42:23
* @Entity generator.domain.Todo
*/
@Mapper
public interface TodoMapper extends BaseMapper<Todo> {
    List<Todo> getTodayList(@Param("todayDate") String date,@Param("userId") Long userId);
}




