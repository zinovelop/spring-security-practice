package com.jinho.spring_security_practice.controller;

import com.jinho.spring_security_practice.dto.JoinDTO;
import com.jinho.spring_security_practice.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return "redirect:/login";
    }
}
