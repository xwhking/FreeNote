package com.xwhking.freenotebackend.model.request;

import lombok.Data;

@Data
public class PasswordRequest {
    private String oldPassword;
    private String newPassword;

}
