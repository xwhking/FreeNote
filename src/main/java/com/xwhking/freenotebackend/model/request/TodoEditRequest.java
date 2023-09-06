package com.xwhking.freenotebackend.model.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class TodoEditRequest implements Serializable {
    private static final long serialVersionUID = -1137331399059324900L;
    private Long id;
    private String title;
    private String profile;
    private Integer isFinish;
}
