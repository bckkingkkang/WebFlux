package com.example.kahyun.controller;

import com.example.kahyun.VO.LoginVO;
import com.example.kahyun.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/user/login")
    public Mono<String> login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "exception", required = false) String exception, ServerWebExchange exchange) {
        return exchange.getPrincipal()
                .map(principal -> {
                    if (principal instanceof AnonymousAuthenticationToken) {
                        return "user/login";
                    } else {
                        return "main";
                    }
                })
                .switchIfEmpty(Mono.just("user/login"))
                .doOnNext(model -> {
                    if (error != null) {
                        // 에러 관련 처리 추가
                    }
                    if (exception != null) {
                        // 예외 관련 처리 추가
                    }
                });
    }

    @GetMapping("/user/signup")
    public Mono<String> signup() {
        return Mono.just("user/signup");
    }

    @ResponseBody
    @PostMapping("/user/signup")
    public Mono<ResponseEntity<String>> signupForm(@RequestBody LoginVO loginVO) {
        loginVO.setPassword(passwordEncoder.encode(loginVO.getPassword())); // 비밀번호 암호화
        loginVO.setCreate_dt(LocalDateTime.now()); // 생성일자 설정
        return loginService.registerUser(loginVO)
                .map(user -> ResponseEntity.ok("회원가입이 완료되었습니다."))
                .defaultIfEmpty(ResponseEntity.badRequest().body("회원가입에 실패했습니다."));
    }

    @PostMapping("/user/loginForm")
    public String loginForm() {
        return "redirect:/main";
    }
}
