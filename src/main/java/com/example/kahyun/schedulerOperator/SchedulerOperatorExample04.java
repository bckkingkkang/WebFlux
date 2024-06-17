package com.example.kahyun.schedulerOperator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    subscribeOn() : 구독 직후에 실행될 쓰레드를 지정한다.
    즉, 원본 Publisher 의 실행 쓰레드를 subscribeOn() 에서 지정한 쓰레드로 바꾼다.
*/

@Slf4j
public class SchedulerOperatorExample04 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(data -> log.info("# doOnNext fromArray : {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter {} : ", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map {} : ", data))
                .subscribe(data -> log.info("# doOnNext : {}", data));

        Thread.sleep(500L);

    }
}
