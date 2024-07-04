package com.example.kahyun.example.contextFeature;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

// 동일한 key 에 대해서 write 할 경우, 해당 키에 대한 값을 덮어쓴다.

@Slf4j
public class ContextFeatureExample03 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";

        Mono.deferContextual(ctx ->
                Mono.just("ID : " + ctx.get(key1)))
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.put(key1, "kahyun"))
                .contextWrite(context -> context.put(key1, "kkahyun"))
                .subscribe(data -> log.info(data));

        // 실행 결과 : kahyun

        Thread.sleep(100L);
    }
}
