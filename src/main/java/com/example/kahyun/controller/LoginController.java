package com.example.kahyun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class LoginController {
    @GetMapping("/user/login")
    public Mono<String> login() {
        return Mono.just("user/login");
    }

    @GetMapping("/user/signup")
    public Mono<String> signup() {
        return Mono.just("user/signup");
    }
}
