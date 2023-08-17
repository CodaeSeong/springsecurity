package com.jwt.security.repository;

import com.jwt.security.entity.EmployeeAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeAuthorityRepository extends JpaRepository<EmployeeAuthority,String> {
}
