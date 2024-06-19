package com.example.kahyun.example.flux;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class FluxExample3 {
    public static void main(String[] args) {
        Flux<Object> flux =
                // concatWith : Mono와 Mono를 합쳐주는 역할의 operator
                // just operator의 경우 null값을 포함할 수 없지만 justOrEmpty의 경우 null 값 포함 가능
                Mono.justOrEmpty(null)
                        .concatWith(Mono.justOrEmpty("Jobs"));

        // ConcatWith 를 이용해서 Mono 와 Mono 를 결합해 새로운 Flux 를 생성할 수 있다.

        flux.subscribe(data->log.info("# result : {}", data));
    }
}
