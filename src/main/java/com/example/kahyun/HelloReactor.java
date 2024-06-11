package com.example.kahyun;

import reactor.core.publisher.Mono;

public class HelloReactor {
    public static void main(String[] args) {
        Mono.just("Hello reactor")
                .subscribe(message -> System.out.println(message));
    }
}
