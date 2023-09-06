package com.xwhking.freenotebackend.model.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class HabitClockInRequest implements Serializable {
    private static final long serialVersionUID = 1288375623351940193L;
    private Long habitId;
    private String profile;
}
