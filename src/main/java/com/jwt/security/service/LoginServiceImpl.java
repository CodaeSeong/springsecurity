package com.jwt.security.service;

import com.jwt.security.entity.EmployeeSecret;
import com.jwt.security.model.RegisterModel;
import com.jwt.security.repository.EmployeeSecretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private EmployeeSecretRepository employeeSecretRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void loginSystem(EmployeeSecret user){

        user.setAuthorityCode("USER");

        String rawUserPassword = user.getUserPassword();
        String encPassword = bCryptPasswordEncoder.encode((rawUserPassword));
        user.setUserPassword(encPassword);

        System.out.println("user = " + user);

        employeeSecretRepository.save(user);
    }
}
