package com.xwhking.freenotebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwhking.freenotebackend.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 28374
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-08-14 16:48:18
* @Entity com.xwhking.freenotebackend.model.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




