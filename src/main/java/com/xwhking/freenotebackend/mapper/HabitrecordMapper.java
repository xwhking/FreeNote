package com.xwhking.freenotebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwhking.freenotebackend.model.entity.Habitrecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 28374
* @description 针对表【habitrecord(习惯打卡表)】的数据库操作Mapper
* @createDate 2023-08-15 21:07:27
* @Entity generator.domain.Habitrecord
*/
@Mapper
public interface HabitrecordMapper extends BaseMapper<Habitrecord> {
    Habitrecord getRecord(@Param("date")String date,@Param("habitId")Long habitId);
}




