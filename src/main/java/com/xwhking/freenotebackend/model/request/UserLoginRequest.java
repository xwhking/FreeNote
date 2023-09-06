package com.xwhking.freenotebackend.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 4460331418005184509L;
    private String userName;
    private String email;
    private String loginCode;
    private String userPassword;
}
