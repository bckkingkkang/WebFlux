package com.example.kahyun.example.contextFeature;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

// Context 는 체인의 맨 아래에서부터 위로 전파된다.
// 따라서 Operator 체인에서 Context read 가 Context Write 밑에 있을 경우에는 write 된 값을 read 할 수 없다.

@Slf4j
public class ContextFeatureExample02 {
    public static void main(String[] args) throws InterruptedException {
        final String key1 = "id";
        final String key2 = "name";

        Mono.deferContextual(ctx -> Mono.just(ctx.get(key1)))
                .publishOn(Schedulers.parallel())
                .contextWrite(ctx -> ctx.put(key2, "kahyun"))
                .transformDeferredContextual((mono,ctx) ->
                        mono.map(data -> data + ", " + ctx.getOrDefault(key2, "Ko Kahyun")))

                .contextWrite(context -> context.put(key1, "kkhyun"))
                .subscribe(data -> log.info(data));

        // 실행 결과 : kkhyun, Ko Kahyun

        Thread.sleep(100L);
    }
}
