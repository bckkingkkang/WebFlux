package com.example.kahyun.controller;

import com.example.kahyun.configuration.SecurityConfig;
import com.example.kahyun.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    @RequestMapping("/main")
    public Mono<String> main() {

        return ReactiveSecurityContextHolder.getContext()
                .doOnNext(securityContext -> {
                    Authentication authentication = securityContext.getAuthentication();
                    String userId = (String) authentication.getPrincipal();
                    System.out.println("로그인된 유저 : " + userId);
                    System.out.println("authentication : "+authentication);
                })
                .then(Mono.just("/main"));
    }

    @GetMapping("/detail/{seq}")
    public Mono<String> detail(@PathVariable int seq) {
        return Mono.just("detail" + seq);
    }
}
