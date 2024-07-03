package com.example.kahyun.example.SchedulerExample;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulersNewParallelExample {
    public static void main(String[] args) throws InterruptedException {
        Mono<Integer> flux =
                Mono.just(1)
                        .publishOn(Schedulers.newParallel("Parallel Thread", 4, true));

        flux.subscribe(data -> {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("onNext subscribe 1 : {}", data);
        });
        flux.subscribe(data -> {
            try {
                Thread.sleep(4000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("onNext subscribe 2 : {}", data);
        });
        flux.subscribe(data -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("onNext subscribe 3 : {}", data);
        });
        // 지연시간이 짧은 쓰레드부터 처리
        flux.subscribe(data -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("onNext subscribe 4 : {}", data);
        });

        Thread.sleep(6000L);
    }
}
