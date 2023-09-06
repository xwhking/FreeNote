package com.xwhking.freenotebackend.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import com.xwhking.freenotebackend.common.BaseResponse;
import com.xwhking.freenotebackend.common.ErrorCode;
import com.xwhking.freenotebackend.common.ResultUtils;
import com.xwhking.freenotebackend.exception.BusinessException;
import com.xwhking.freenotebackend.model.dto.UserDTO;
import com.xwhking.freenotebackend.model.entity.Daily;
import com.xwhking.freenotebackend.model.entity.Sentences;
import com.xwhking.freenotebackend.model.request.SentenceRequest;
import com.xwhking.freenotebackend.service.SentencesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.xwhking.freenotebackend.constant.SentenceType.SENTENCE_TYPES;
import static com.xwhking.freenotebackend.constant.UserConstant.CURRENT_USER;

@RestController
@RequestMapping("/sentence")
public class SentenceController {
    @Resource
    private SentencesService sentencesService;

    @GetMapping("/getOne")
    public BaseResponse<Sentences> getOne(SentenceRequest sentenceRequest, HttpServletRequest request){
        // 判断是否登录
        UserDTO loginUser = (UserDTO)request.getSession().getAttribute(CURRENT_USER);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 校验参数
        if(sentenceRequest == null || sentenceRequest.getIsRandom() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空，或者是否随机参数为空");
        }
        // todo 是否可以把数据存到redis然后取数据
        // 随机返回一句话
        Random random = new Random(new Date().getTime());
        if(sentenceRequest.getIsRandom() || sentenceRequest.getType() == null || "".equals(sentenceRequest.getType())) {
            List<Sentences> allSentence = sentencesService.list();
            int size = allSentence.size();
            return ResultUtils.success(allSentence.get(random.nextInt(size)));
        }else{
            LambdaQueryWrapper<Sentences> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            if(!SENTENCE_TYPES.contains(sentenceRequest.getType())){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"不存在分类") ;
            }
            lambdaQueryWrapper.eq(Sentences::getType,sentenceRequest.getType());
            List<Sentences> typeSentences = sentencesService.list(lambdaQueryWrapper);
            Sentences returnSentence = typeSentences.get(random.nextInt(typeSentences.size()));
            returnSentence.setTimes(returnSentence.getTimes()+1);
            sentencesService.updateById(returnSentence);
            return ResultUtils.success(returnSentence);
        }
    }

    @GetMapping("/daily")
    public BaseResponse<Daily> getDaily(){
        HttpResponse body = HttpUtil.createGet("https://open.iciba.com/dsapi/").execute();
        String result = body.body();
        Gson gson = new Gson();
        Daily daily = gson.fromJson(result, Daily.class);
        return ResultUtils.success(daily);
    }
}
