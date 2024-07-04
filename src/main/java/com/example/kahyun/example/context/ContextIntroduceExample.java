package com.example.kahyun.example.context;

/*
    Context 개념 설명 예제
    - contextWrite() 으로 Context 에 값을 저장할 수 있고, ContextView.get() 을 통해서 Context 에 저장된 값을 road 할 수 있다.
    - ContextView 는 deferContextual() 또는 transformDeferredContextual() 을 통해 제공된다.
*/

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ContextIntroduceExample {
    public static void main(String[] args) throws InterruptedException {
        // "message" key 로 지정
        String key = "message";

        Mono<String> mono = Mono.deferContextual(ctx ->
                Mono.just("hello" + " " + ctx.get(key)).doOnNext(data -> log.info("doOnNext : {}", data))
        )
                // 실행 쓰레드가 다른 경우에서도 context 내부에 저장된 상태값 공유 사용 가능
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                .transformDeferredContextual((mono2, ctx) -> mono2.map(data -> data + " " + ctx.get(key)))

                // key, value 형태로 저장
                .contextWrite(context -> context.put(key, "Reactor"));

        mono.subscribe(data -> log.info("onNext data : {}", data));

        Thread.sleep(100L);

    }
}
