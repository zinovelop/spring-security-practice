package com.jinho.spring_security_practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
