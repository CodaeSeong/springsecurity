package com.jwt.security.controller;

import com.jwt.security.config.auth.PrincipalDetails;
import com.jwt.security.entity.EmployeeSecret;
import com.jwt.security.model.RegisterModel;

import com.jwt.security.service.RegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private RegistService registService;

    @GetMapping("/test/login")
    @ResponseBody
    public String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
        System.out.println("/test/login================= = ");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication = " + principalDetails.getUser());

        System.out.println("userDetails = " + userDetails.getUser());


        return "세션정보 확인하기";

    }

    @GetMapping("/test/oauth/login")
    @ResponseBody
    public String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
        System.out.println("/test/login================= = ");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication = " + oAuth2User.getAttributes());
        System.out.println("oauth.getAttributes() = " + oauth.getAttributes());


        return "OAuth 세션 정보 확인하기";

    }

    @GetMapping({"", "/"})
    @ResponseBody
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails.getUser() = " + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/login")
    public String login() {
        return "/";
    }

    @GetMapping("/registerForm")
    public String registerForm() {

        return "registerForm";
    }

    @PostMapping("/join")
    public String join(EmployeeSecret user) {

        registService.registSystem(user);

        return "redirect:/loginForm";
    }

    @GetMapping("/info")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @ResponseBody
    public String info() {
        return "info";
    }

}
