package com.example.kahyun.schedulerOperator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    Operator 체인에서 publishOn() 이 호출되면 publishOn() 호출 이후의 Operator 체인은
    다음 publishOn() 을 만나기 전까지 publishOn()에서 지정한 Thread 에서 실행이 된다.
*/

@Slf4j
public class SchedulerOperatorExample03 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .doOnNext(data -> log.info("doOnNext : {}", data))
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("filter : {}", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("map : {}", data))
                .subscribe(data -> log.info("onNext : {}", data));
        Thread.sleep(500L);
    }
}
