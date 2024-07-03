package com.example.kahyun.example.SchedulerExample;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulersNewSingleExample {
    public static void main(String[] args) throws InterruptedException {

        doTask("task1")
                .subscribe(data -> log.info("task1 : {}",data));

        doTask("task2")
                .subscribe(data -> log.info("task2",data));

        Thread.sleep(200L);
    }
    private static Flux<Integer> doTask(String taskName) {
        return Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .doOnNext(data -> log.info(taskName, "fromArray : {}", data))
                .publishOn(Schedulers.newSingle("new-single", true))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info(taskName, "filter : {}", data))
                .map(data -> data * 3)
                .doOnNext(data -> log.info(taskName, "map : {}", data));
    }
}
