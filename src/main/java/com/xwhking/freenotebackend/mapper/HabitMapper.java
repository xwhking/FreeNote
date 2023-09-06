package com.xwhking.freenotebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwhking.freenotebackend.model.entity.Habit;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 28374
* @description 针对表【habit(习惯表)】的数据库操作Mapper
* @createDate 2023-08-15 21:07:27
* @Entity generator.domain.Habit
*/
@Mapper
public interface HabitMapper extends BaseMapper<Habit> {

}




