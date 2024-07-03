package com.example.kahyun.example.SchedulerExample;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
* Schedulers.immediate() 적용 후 현재 쓰레드 할당
* */
@Slf4j
public class SchedulersImmediateExample {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("filter : {}", data))
                .publishOn(Schedulers.immediate())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("map : {}", data))
                .subscribe(data -> log.info("subscribe : {}", data));

        Thread.sleep(200L);
    }
}
