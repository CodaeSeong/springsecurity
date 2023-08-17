package com.jwt.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EmployeeAuthority {
    @Id
    private String authorityCodeNumber;

    private String empCode;

    private String Remark;


}
