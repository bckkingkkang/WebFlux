package com.example.kahyun.backpressureexample;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/*
    Unbounded request 일 경우, Downstream 에 Backpressure DROP 전략을 적용
    - Downstream 으로 전달할 데이터가 Buffer 에 가득 찰 경우, Buffer 밖에서 대기하는 먼저 emit 된 데이터를 Drop 시키는 전략
*/

@Slf4j
public class BackpressureStrategyDropExample {
    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureDrop(dropped -> log.info("# dropped: {}", dropped))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            try {
                                Thread.sleep(5L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            log.info("# onNext : {}", data);

                },
                        error -> System.out.println(error));
        Thread.sleep(2000L);
    }
}
