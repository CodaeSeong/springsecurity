package com.hec.stp.global.config;

import java.util.Arrays;
import java.util.Collections;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.hec.stp.global.jwt.JwtAccessDeniedHandler;
import com.hec.stp.global.jwt.JwtAuthenticationEntryPoint;
import com.hec.stp.global.jwt.JwtSecurityFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Resource(name="jwtAuthenticationEntryPoint")
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Resource(name="jwtAccessDeniedHandler")
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
        	.formLogin().disable()
			.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/**/er/**").permitAll()
            .antMatchers("/api/bs/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_COMPANY", "ROLE_PARTNER", "ROLE_CUSTOMER")
            .antMatchers("/api/mg/**").hasAnyAuthority("ROLE_MANAGER")
            .antMatchers("/api/cn/**").hasAnyAuthority("ROLE_COMPANY")
            .antMatchers("/api/pn/**").hasAnyAuthority("ROLE_PARTNER")
            .antMatchers("/api/cm/**").hasAnyAuthority("ROLE_CUSTOMER")
				.antMatchers("/test/data").hasAnyAuthority("ROLE_MANAGER")
            .anyRequest().permitAll()
            .and()
	        .exceptionHandling()
	        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
	        .accessDeniedHandler(jwtAccessDeniedHandler);
        http.addFilterBefore(new JwtSecurityFilter(), UsernamePasswordAuthenticationFilter.class);
        http.cors();
        http.headers().frameOptions().disable();
        return http.build();
    }
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		// 스프링 시큐리티 커스텀 필터 수행 제외 로직
		return (web) -> web.ignoring()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
				.antMatchers("/", "/resourse/**", "/swagger-ui/**", "/api-docs/**", "/files/**", "/editor/**");
	}	
}
