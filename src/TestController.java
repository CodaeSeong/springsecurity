package com.hec.stp.domain.api.bs.er.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("loginForm")
    public String loginForm() {
        return "loginForm";
    }
    @GetMapping("test/jsp/home")
    public String testhome() {
        return "homepage";
    }
@ResponseBody
    @GetMapping("/api/bs/co/test")
    public String testdata() {
        return "good";
    }
}
