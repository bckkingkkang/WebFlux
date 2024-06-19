package com.example.kahyun.example.schedulerOperator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulerOperatorExample06 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7,})
                .doOnNext(data -> log.info("# doOnNext fromArray: {}", data))
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter: {}", data))
                .subscribeOn(Schedulers.boundedElastic())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map: {}", data))
                .subscribe(data -> log.info("# doOnNext subscribe: {}", data));

        Thread.sleep(500L);
    }
}
