package com.example.kahyun.example.contextAPI;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@Slf4j
public class ContextAPIExample01 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";
        String key2 = "name";

        Mono<String> mono =
                Mono.deferContextual(ctx ->
                        Mono.just("ID : " + ctx.get(key1) + ", " + "Name : " + ctx.get(key2))
                )
                        .publishOn(Schedulers.parallel())

                        // context 에 값을 저장하기 위해서는 contextWrite()을 사용
                        // Context.of(key1, value1, key2, value2, ... ) : key/value 형태로 Context 에 여러 개의 값을 쓴다.
                        .contextWrite(Context.of(key1, "kahyun", key2, "Ko Kahyun"));

        mono.subscribe(data -> log.info(data));

        Thread.sleep(100L);
    }
}
