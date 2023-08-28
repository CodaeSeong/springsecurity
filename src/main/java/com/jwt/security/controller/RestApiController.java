package com.jwt.security.controller;


import com.jwt.security.entity.JwtUser;
import com.jwt.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class RestApiController {

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private final UserRepository userRepository;


    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("/token")
    public String token() {
        return "<h1>token</h1>";
    }

    @GetMapping("/api/v1/user/{test}")
    public String user(@PathVariable String test) {
        return "<h1>user</h1>";
    }

    @GetMapping("/api/v1/manager/{test}")
    public String manager(@PathVariable String test) {
        return "<h1>manager</h1>";
    }

    @GetMapping("/api/v1/admin/{test}")
    public String admin(@PathVariable String test) {
        return "<h1>admin</h1>";
    }

    @PostMapping("join")
    public String join(@RequestBody JwtUser jwtUser) {
        jwtUser.setPassword(bCryptPasswordEncoder.encode(jwtUser.getPassword()));
        jwtUser.setRoles("ROLE_USER");
        userRepository.save(jwtUser);
        System.out.println("회원가입 완료");
        return "회원가입완료";
    }
}
