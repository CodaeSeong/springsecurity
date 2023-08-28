package com.jwt.security.repository;

import com.jwt.security.entity.JwtUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<JwtUser,Long> {

    JwtUser findByUsername(String username);
}
