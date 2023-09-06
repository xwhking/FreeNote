package com.xwhking.freenotebackend.model.request;

import lombok.Data;

@Data
public class TabTaskListRequest {
    private String searchTitle;
    private Long tabId;
}
