package com.xwhking.freenotebackend.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TodoCreateRequest implements Serializable {
    private static final long serialVersionUID = -2854662516821000731L;
    private String title;
    private String todoProfile;
}
