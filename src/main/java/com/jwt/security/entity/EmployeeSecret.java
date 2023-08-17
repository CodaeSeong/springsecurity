package com.jwt.security.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class EmployeeSecret {

    @EmbeddedId
    private EmployeeSecretId id;

    private String userPassword;

    private String authorityCode;
}
