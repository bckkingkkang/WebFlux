package com.example.kahyun.example.SchedulerExample;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulersNewBoundedElasticExample {
    public static void main(String[] args) {
        Scheduler scheduler = Schedulers.newBoundedElastic(2, 2, "I/O Thread");

        Mono<Integer> mono =
                Mono
                        .just(1)
                        .subscribeOn(scheduler);

        log.info("# start");

        mono.subscribe(data -> {
            log.info("subscribe 1 doing", data);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("subscribe 1 done", data);
        });

        mono.subscribe(data -> {
            log.info("subscribe 2 doing", data);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("subscribe 2 done", data);
        });

        mono.subscribe(data -> {
            log.info("subscribe 3 doing", data);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("subscribe 3 done", data);
        });
        mono.subscribe(data -> {
            log.info("subscribe 4 doing", data);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("subscribe 4 done", data);
        });
        mono.subscribe(data -> {
            log.info("subscribe 5 doing", data);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("subscribe 5 done", data);
        });
        mono.subscribe(data -> {
            log.info("subscribe 6 doing", data);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("subscribe 6 done", data);
        });
    }
}
