package com.jwt.security.repository;

import com.jwt.security.entity.EmployeeBasic;
import com.jwt.security.entity.EmployeeBasicId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeBasicRepository extends JpaRepository<EmployeeBasic, EmployeeBasicId> {
}
