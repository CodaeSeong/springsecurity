package com.jwt.security.service;

import com.jwt.security.entity.EmployeeSecret;
import com.jwt.security.model.RegisterModel;

public interface LoginService {
    public void loginSystem(EmployeeSecret user);

}
