package com.example.kahyun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class LoginController {
    @GetMapping("/login")
    public Mono<String> login() {
        return Mono.just("login");
    }
}
