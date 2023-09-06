package com.xwhking.freenotebackend.model.request;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
public class HabitCreateRequest implements Serializable {
    private static final long serialVersionUID = 2680038908269067657L;
    private String title;
    private String habitProfile;
    private LocalDate endTime;
}
