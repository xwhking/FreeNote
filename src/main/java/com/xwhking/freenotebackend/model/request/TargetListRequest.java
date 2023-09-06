package com.xwhking.freenotebackend.model.request;

import lombok.Data;

@Data
public class TargetListRequest extends MyPageRequest{
    private String searchTitle;
    private String searchContent;
    private Integer isFinish;
}
