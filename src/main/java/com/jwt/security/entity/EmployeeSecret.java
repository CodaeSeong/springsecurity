package com.jwt.security.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class EmployeeSecret {

    @Id
    private String empCode;

    private String companyCode;

    private String userPassword;

    private String authorityCode;
}
