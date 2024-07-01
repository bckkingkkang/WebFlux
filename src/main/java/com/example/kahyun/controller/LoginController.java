package com.example.kahyun.controller;

import com.example.kahyun.VO.LoginVO;
import com.example.kahyun.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;


    // 로그인 화면
    @GetMapping("/user/login")
    public Mono<String> login() {
        return Mono.just("/user/login");
    }

    // 회원가입 화면
    @GetMapping("/user/signup")
    public Mono<String> signup() {
        return Mono.just("user/signup");
    }

    // 회원가입
    @ResponseBody
    @PostMapping("/user/signup")
    public Mono<ResponseEntity<String>> signupForm(@RequestBody LoginVO loginVO) {
        // 비밀번호 암호화
        loginVO.setPassword(passwordEncoder.encode(loginVO.getPassword()));
        // 현재 시간 설정
        loginVO.setCreate_dt(LocalDateTime.now());
        // 사용자 등록
        return loginService.registerUser(loginVO)
                // 회원 가입 성공
                .map(user -> ResponseEntity.ok("회원가입이 완료되었습니다."))
                // 회원 가입 실패
                .defaultIfEmpty(ResponseEntity.badRequest().body("회원가입에 실패했습니다."));
                /*
                    defaultIfEmpty : 반환된 Mono가 비어있는 경우
                */
    }

    @RequestMapping("/user/logout")
    public Mono<String> logout() {
        return Mono.just("/user/logout");
    }

}
