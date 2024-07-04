package com.example.kahyun.example.contextFeature;

/*
    Context 의 특징

    - inner sequence 내부에서는 외부 context 에 저장된 데이터를 읽을 수 없다.
    - inner sequence 내부에서 context 에 저장된 데이터는 inner sequence 외부에서 읽을 수 없다.
*/

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ContextFeatureExample04 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";

        Mono.just("KAHYUN")
                // inner sequence 내부에서 정의된 "job" 불러올 수 없음
                //.transformDeferredContextual((stringMono, contextView) -> contextView.get("job"))

                // flatMap operator 내부에 새로운 Mono 또는 Flux Sequence 를 정의할 수 있다.
                .flatMap(name -> Mono.deferContextual(ctx ->
                        // inner sequence
                        Mono.just(ctx.get(key1) + ", " + name)
                                .transformDeferredContextual((mono, innerCtx) ->
                                        mono.map(data -> data + ", " + innerCtx.get("job"))
                                )
                                .contextWrite(context -> context.put("job", "Software Engineer"))
                ))

                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.put(key1, "kahyun"))
                .subscribe(data -> log.info(data));

        Thread.sleep(100L);
    }
}
