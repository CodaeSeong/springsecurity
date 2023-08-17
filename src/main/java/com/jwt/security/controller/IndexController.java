package com.jwt.security.controller;

import com.jwt.security.entity.EmployeeSecret;
import com.jwt.security.model.RegisterModel;
import com.jwt.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private LoginService loginService;

    @GetMapping({"","/"})
    @ResponseBody
    public  String index() {
        return "index";
    }

    @GetMapping("/user")
    @ResponseBody
    public  String user() {
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

        loginService.loginSystem(user);

        return "redirect:/loginForm";
    }
}
