package com.xwhking.freenotebackend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import  com.xwhking.freenotebackend.model.entity.Sentences;
import  com.xwhking.freenotebackend.mapper.SentencesMapper;
import  com.xwhking.freenotebackend.service.SentencesService;
import org.springframework.stereotype.Service;

/**
* @author 28374
* @description 针对表【sentences(句子)】的数据库操作Service实现
* @createDate 2023-08-18 17:25:06
*/
@Service
public class SentencesServiceImpl extends ServiceImpl<SentencesMapper, Sentences>
    implements SentencesService{

}




