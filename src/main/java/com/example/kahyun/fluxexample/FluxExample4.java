package com.example.kahyun.fluxexample;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class FluxExample4 {
    public static void main(String[] args) {
        Flux.concat(
                Mono.just("Venus"),
                Flux.just("Earth"),
                Flux.just("Moon", "Sun"),
                Flux.just("Mars"))
                // 하나의 리스트 안에 각각의 데이터들이 담겨 리스트로 전달
                .collectList()
                .subscribe(planetList -> log.info("# Solar System : {}", planetList)
        );
    }

    // ConcatWith : 앞쪽에 있는 Publisher와 ConcatWith의 parameter로 입력된 Publisher를 연결
    // concat : concat operator의 parameter로 입력된 Publisher 들을 연결
}
