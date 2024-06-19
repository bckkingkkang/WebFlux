package com.example.kahyun.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public Mono<String> main() {
        return Mono.just("main");
    }

    @GetMapping("/detail/{seq}")
    public Mono<String> detail(@PathVariable int seq) {
        return Mono.just("detail" + seq);
    }
}
