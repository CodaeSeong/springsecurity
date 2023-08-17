package com.jwt.security.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class EmployeeBasicId implements Serializable {

    private String companyCode;

    private String empCode;
}
