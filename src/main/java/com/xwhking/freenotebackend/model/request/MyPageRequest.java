package com.xwhking.freenotebackend.model.request;

import lombok.Data;
@Data
public class MyPageRequest {
    /**
     * 当前页数
     */
    private int page = 1;
    /**
     * 当前页面条数
     */
    private int pageNum = 10;
}
