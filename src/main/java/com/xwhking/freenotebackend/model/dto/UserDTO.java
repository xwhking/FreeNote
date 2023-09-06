package com.xwhking.freenotebackend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private Long id;
    private String email;
    private String userName;
    private String userAvatar ;
    private String userProfile;
    private Integer userRole;
    private static final long serialVersionUID = 1L;
}
