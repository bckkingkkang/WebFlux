package com.example.kahyun;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoExample1 {
    public static void main(String[] args) {
        /* Mono example 1 */
        Mono.just("Hello reactor")
                .subscribe(message -> System.out.println(message));     // downstream
    }
}
