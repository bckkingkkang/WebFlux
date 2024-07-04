package com.example.kahyun.example.contextAPI;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

/*
    Context API 예제 코드
    - putAll(ContextView) API 사용
*/

@Slf4j
public class ContextAPIExample02 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";
        String key2 = "name";
        String key3 = "country";

        // ContextView는 Reactor Sequence에서 deferContextual() 또는 transformDeferredContextual()을 통해서 제공된다.
        Mono.deferContextual(ctx ->
                Mono.just("ID : " + ctx.get(key1) + ", Name : " + ctx.get(key2) + ", Country : " + ctx.get(key3)))
                .publishOn(Schedulers.parallel())
                /*
                    putAll(ContextView) : 파라미터로 입력된 ContextView를 merge한다.
                                          기존의 Context와 파라미터로 입력받은 ContextView의 데이터를 하나로 합쳐서 새로운 Context를 리턴
                */
                .contextWrite(context -> context.putAll(Context.of(key2, "Ko Kahyun", key3, "Korea").readOnly()))
                // put(key, value) : key/value 형태로 Context에 값을 쓴다.
                .contextWrite(context -> context.put(key1, "kkhyun"))
                .subscribe(data -> log.info(data));

        Thread.sleep(100L);
    }
}
