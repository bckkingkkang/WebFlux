package com.example.kahyun.monoexample;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoExample2 {
    public static void main(String[] args) {

        /* Mono example 2 */
        Mono.empty()    // empty operator -> 데이터 전달을 하지 않고 onComplete signal만 전송
                .subscribe(
                        // upstream 으로부터 emit 된 데이터를 전달받는 부분
                        data -> log.info("# emitted data : {}", data),
                        // upstream 에서 에러가 발생했을 경우 전달받는 부분
                        error -> {},
                        // upstream 으로부터 onComplete signal을 전달받는 부분
                        () -> log.info("# emitted onComplete signal")
                );
    }
}
