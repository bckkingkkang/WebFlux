package com.example.kahyun.example.contextAPI;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@Slf4j
public class ContextAPIExample3 {
    public static void main(String[] args) throws InterruptedException {
        String key1 = "id";
        String key2 = "name";

        Mono.deferContextual(ctx ->
                // getOrDefault(key, default value) : ContextView에서 key에 해당하는 value를 가져온다. key에 해당하는 value가 없으면 default value를 가져온다.
                Mono.just("ID : " + ctx.get(key1) + ", Name : " + ctx.get(key2) + ", Job : " + ctx.getOrDefault("job", "Software Engineer")))
                .publishOn(Schedulers.parallel())
                .contextWrite(Context.of(key1, "kkhyun", key2, "kahyun"))
                .subscribe(data -> log.info(data));

        Thread.sleep(100L);
    }
}
