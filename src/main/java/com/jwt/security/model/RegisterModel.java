package com.jwt.security.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RegisterModel {

    private String companyCode;

    private String empCode;

    private String userPassword;

    private String authorityCode;
}
