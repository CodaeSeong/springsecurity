package com.jwt.security.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Data
public class EmployeeSecretId implements Serializable {

    private String companyCode;

    private String empCode;
}
