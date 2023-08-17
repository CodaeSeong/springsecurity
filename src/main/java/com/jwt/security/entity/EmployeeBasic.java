package com.jwt.security.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EmployeeBasic {
    @EmbeddedId
    private EmployeeBasicId id;

    private String empName;

    private String empEngName;

    private String socialSecurityNumber;

    private String hireDate;

    private String retirementDate;

    private String userOrNot;

    private String birthDate;

    private String gender;

    private String deptCode;

    private String color;

    private String img;
}
