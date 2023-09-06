package com.xwhking.freenotebackend.model.request;

import lombok.Data;

@Data
public class HabitListRequest extends MyPageRequest{
    private String searchText;
}
