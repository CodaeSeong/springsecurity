package com.jwt.security.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.security.config.DTO.UserDTO;
import com.jwt.security.config.auth.PrincipalDetails;
import com.jwt.security.entity.JwtUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// login 요청해서 username,possword 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작을 함.

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter: 로그인 시도중");

        // 1.usernmae, password 받아서
        try {
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while((input=br.readLine()) != null){
//                System.out.println(input);
//            }

            ObjectMapper om = new ObjectMapper();
            UserDTO user = om.readValue(request.getInputStream(), UserDTO.class);
            System.out.println("user = " + user);


            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            System.out.println("JwtAuthenticationFilter : 토큰생성완료");
            // PrincipalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication 이 리턴됨.
            // DB에 있는 username과 password가 일치한다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);


            // authentication 객체가 session 영역에 저장됨. => 로그인이 되었다.
            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에편하려고 하는거임
            // 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리 때문에 session에 넣어 줍니다.

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료됨 :principalDetails = " + principalDetails.getJwtUser());


            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 2. 정상인지 로그인 시도를 해보는 것 authenticationManager로 로그인 시도를 하면!!
        // 3. PrincipalDetails를 세션에 담고 (권한 관리를 위해서)

        // 4. JWT토큰을 만들어서 응답해주면 됨.
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행
    // JWT 토큰을 만들어서 request요청한 사용자에게 JWT토큰을 response해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨: 인증이 완료되었다는 뜻임.");
        PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

        //RSA방식은 아니고 HASH암호방식
        String jwtToken = JWT.create()
                .withSubject(principalDetailis.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetailis.getJwtUser().getId())
                .withClaim("username", principalDetailis.getJwtUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);

    }


}
