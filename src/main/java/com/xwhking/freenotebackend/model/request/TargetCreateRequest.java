package com.xwhking.freenotebackend.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TargetCreateRequest {
    private String title;
    private String content;
    private LocalDate endTime;
}
