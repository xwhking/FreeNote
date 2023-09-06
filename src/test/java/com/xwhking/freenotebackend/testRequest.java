package com.xwhking.freenotebackend;


import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.xwhking.freenotebackend.model.entity.Daily;

public class testRequest {
    public static void main(String[] args) {
        HttpResponse body = HttpUtil.createGet("https://open.iciba.com/dsapi/").execute();
        String result = body.body();
        System.out.println(result);
        Gson gson = new Gson();
        Daily daily = gson.fromJson(result, Daily.class);
        System.out.println(daily);
    }
}
