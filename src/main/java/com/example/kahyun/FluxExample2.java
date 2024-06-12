package com.example.kahyun;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxExample2 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer[]{3, 6, 7, 9})   // 전달받은 배열 데이터를 각각 downstream 으로 emit
                .filter(num -> num > 6)             // filter operator 에서 필터링되어서 emit
                .map(num -> num * 2)
                .subscribe(multiply -> log.info("# multiply : {}", multiply));
    }
}
