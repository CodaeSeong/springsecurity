package com.jwt.security.auth;

import com.jwt.security.entity.EmployeeSecret;
import com.jwt.security.repository.EmployeeSecretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티 설정에서 loginProcessingUrl("/login")
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeSecretRepository employeeSecretRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        EmployeeSecret user = employeeSecretRepository.findByEmpCode(username);

        if(user != null){
            return new PrincipalDetails(user);
        }

        return null;
    }


}
