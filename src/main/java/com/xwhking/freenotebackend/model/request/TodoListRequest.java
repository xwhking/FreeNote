package com.xwhking.freenotebackend.model.request;

import lombok.Data;

@Data
public class TodoListRequest extends MyPageRequest{
    private Integer isFinish;
    private String searchText;
    private String date; // 日期格式为yyyy-MM-dd
}
