package com.jwt.security.service;

import com.jwt.security.entity.EmployeeSecret;
import com.jwt.security.repository.EmployeeSecretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistServiceImpl implements RegistService {

    @Autowired
    private EmployeeSecretRepository employeeSecretRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void registSystem(EmployeeSecret user){

        user.setRole("ROLE_USER");

        String rawUserPassword = user.getUserPassword();
        String encPassword = bCryptPasswordEncoder.encode((rawUserPassword));
        user.setUserPassword(encPassword);

        System.out.println("user = " + user);

        employeeSecretRepository.save(user);
    }
}
