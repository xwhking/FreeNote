package com.xwhking.freenotebackend.model.dto;

import com.xwhking.freenotebackend.model.entity.Habit;
import com.xwhking.freenotebackend.model.entity.Habitrecord;
import lombok.Data;
import java.util.List;

import java.io.Serializable;

@Data
public class HabitDetailDTO implements Serializable {
    private static final long serialVersionUID = -505571129210081767L;
    private Habit targetHabit;
    private Long finishRecord;
    private Long failRecord;
    private Long surplusRecord;
    private Long totalRecord;
    private List<CalendarData> calendarDataList;
    private List<Habitrecord> habitrecords;
}
