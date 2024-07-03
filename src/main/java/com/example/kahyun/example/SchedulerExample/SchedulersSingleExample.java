package com.example.kahyun.example.SchedulerExample;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
* Schedulers.single()을 적용할 경우
* Schedulers.single() 에서 할당된 쓰레드를 재사용한다.
* */

@Slf4j
public class SchedulersSingleExample {
    public static void main(String[] args) throws InterruptedException {
        doTask("task1")
                .subscribe(data -> log.info("onNext: {}", data));

        doTask("task2")
                .subscribe(data -> log.info("onNext: {}", data));

        Thread.sleep(200L);

    }

    private static Flux<Integer> doTask(String taskName) {
        return Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .publishOn(Schedulers.single())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info(taskName, "filter : {}", data))
                .map(data -> data * 3)
                .doOnNext(data -> log.info(taskName, "map : {}", data));
    }
}
