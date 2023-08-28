package com.jwt.security.config.auth;

import com.jwt.security.entity.JwtUser;
import com.jwt.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepositoy;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUser user = userRepositoy.findByUsername(username);
        return new PrincipalDetails(user);
    }
}
