package com.example.kahyun.example.contextFeature;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

// Context 는 각각의 구독을 통해 Reactor Sequence 에 연결되며 체인의 각 연산자가 연결된 Context에 접근할 수 있어야 한다.

@Slf4j
public class ContextFeatureExample01 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";

        Mono<String> mono = Mono.deferContextual(ctx ->
                Mono.just("ID : " + ctx.get(key1)))
                .publishOn(Schedulers.parallel());

        // subscribe(구독)가 한 번 발생할 때 마다 subscriber 는 자신만의 context 를 가진다.
        mono.contextWrite(context -> context.put(key1, "kkhyun"))
                .subscribe(data -> log.info("subscribe1 {}", data));

        mono.contextWrite(context -> context.put(key1, "kkahyun"))
                .subscribe(data -> log.info("subscribe2 {}", data));

        Thread.sleep(100L);
    }
}
