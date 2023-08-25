package com.jwt.security.model;

import lombok.Data;

@Data
public class RegisterModel {

    private String companyCode;

    private String empCode;

    private String userPassword;

    private String role;
}
