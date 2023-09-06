package com.xwhking.freenotebackend.model.request;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class HabitEditRequest implements Serializable {
    private static final long serialVersionUID = 2680038908269067657L;
    private Long id;
    private String title;
    private String habitProfile;
    private LocalDate endTime;
}
