package com.xwhking.freenotebackend.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -1365107659429815535L;
    private String userName;
    private String email;
    private String registerCode;
    private String userPassword;
}
