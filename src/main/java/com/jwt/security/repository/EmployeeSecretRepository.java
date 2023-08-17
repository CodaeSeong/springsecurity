package com.jwt.security.repository;

import com.jwt.security.entity.EmployeeSecret;
import com.jwt.security.entity.EmployeeSecretId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeSecretRepository extends JpaRepository<EmployeeSecret, EmployeeSecretId> {

}
