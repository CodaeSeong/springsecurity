package com.jwt.security.repository;

import com.jwt.security.entity.EmployeeSecret;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeSecretRepository extends JpaRepository<EmployeeSecret, String> {

    EmployeeSecret findByEmpCode(String empCode);

}
