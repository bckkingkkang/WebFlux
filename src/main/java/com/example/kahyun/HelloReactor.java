package com.example.kahyun;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
public class HelloReactor {
    public static void main(String[] args) {

        Mono.just("Hello reactor")
                .subscribe(message -> System.out.println(message));     // downstream

        // Flux : 데이터를 생성해서 emit 하는 publisher
        Flux<String> sequence = Flux.just("Hello", "World");
        // just 메소드 : emit 될 데이터 정의
        sequence    // operator 체인을 정의해놓은 것
                .map(data -> data.toUpperCase())
                // map 메소드 -> 퍼블리셔 쪽에서 에밋된 데이터들을 전달받은 후 대문자로 변환
                .subscribe(data -> log.info(data));
    }
}
