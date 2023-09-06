package com.xwhking.freenotebackend.model.request;

import lombok.Data;

@Data
public class TabTaskRequest {
    private Long id;
    private String title;
    private Long tabId;
    private String profile;
    private Integer isFinish;
}
