package com.xwhking.freenotebackend.model.request;

import lombok.Data;

@Data
public class TabListRequest extends MyPageRequest{
    private String searchTitle;
}
