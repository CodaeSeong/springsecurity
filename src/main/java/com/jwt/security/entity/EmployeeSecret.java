package com.jwt.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
public class EmployeeSecret {

    @Id
    private String empCode;

    private String companyCode;

    private String userPassword;

    private String role;

    @Builder
    public EmployeeSecret(String empCode, String companyCode, String userPassword, String role) {
        this.empCode = empCode;
        this.companyCode = companyCode;
        this.userPassword = userPassword;
        this.role = role;
    }

    public EmployeeSecret() {
    }
}
