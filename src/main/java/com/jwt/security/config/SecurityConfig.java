package com.jwt.security.config;



import com.jwt.security.config.filter.Filter3;
import com.jwt.security.config.jwt.JwtAuthenticationFilter;
import com.jwt.security.config.jwt.JwtAuthorizationFilter;
import com.jwt.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;


// 1.코드받기(인증)
// 2.엑세스토큰(권한)
// 3. 사용자 프로필 정보를 가져오고
// 4. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
// 4-2 (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> (집주소), 백화점몰 -> (vip등급, 일반등급)
@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {


    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter) // @CrossOrigin(인증X), 시큐리티 필터에 등록 인증(O)
                .formLogin().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
                .httpBasic().disable()
                .authorizeHttpRequests( authorize -> authorize
                        .requestMatchers("/api/v1/user/**")
                        .hasAnyRole("USER","MANAGER","ADMIN")
                        .requestMatchers("/api/v1/manager/**")
                        .hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers("/api/v1/admin/**")
                        .hasRole("ADMIN")
                        .anyRequest().permitAll()

                );

                        return http.build();



    }
}
