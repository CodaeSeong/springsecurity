package com.jwt.security.service;

import com.jwt.security.entity.EmployeeSecret;
import com.jwt.security.entity.EmployeeSecretId;
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
    public void loginSystem(RegisterModel registerModel){

        EmployeeSecret employeeSecret = new EmployeeSecret();
        EmployeeSecretId id = new EmployeeSecretId();

        id.setCompanyCode(registerModel.getCompanyCode());
        id.setEmpCode(registerModel.getEmpCode());

        employeeSecret.setId(id);
        employeeSecret.setAuthorityCode("USER");

        String rawUserPassword = registerModel.getUserPassword();
        String encPassword = bCryptPasswordEncoder.encode((rawUserPassword));
        employeeSecret.setUserPassword(encPassword);

        System.out.println("employeeSecret = " + employeeSecret);

        employeeSecretRepository.save(employeeSecret);
    }
}
