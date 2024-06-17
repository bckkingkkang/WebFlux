package com.example.kahyun.parallel;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ParallelExample01 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15})
                .parallel()     // 병렬로 처리하겠다는 정의
                .subscribe(data -> log.info("# onNext : {}", data));
    }
}

        /*
             결과
            : 병렬로 처리되지 않고 메인 쓰레드에서 전부 처리가 됨
        */
