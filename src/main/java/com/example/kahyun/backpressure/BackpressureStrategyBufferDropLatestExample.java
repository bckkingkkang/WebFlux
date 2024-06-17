package com.example.kahyun.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/*
    Unbounded request 일 경우, Downstream 에 Backpressure Buffer DROP_LATEST 전략을 적용

    - Downstream 으로 전달할 데이터가 Buffer 에 가득 찰 경우,
      Buffer 안에 있는 데이터 중에서 가장 최근(나중)에 Buffer 로 들어온 데이터부터 DROP
*/

@Slf4j
public class BackpressureStrategyBufferDropLatestExample {
    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(300L))
                .doOnNext(data -> log.info("# emitted by original Flux : {}", data))
                .onBackpressureBuffer(2,
                        dropped -> log.info("# Overflow & Dropped : {}", dropped),
                        BufferOverflowStrategy.DROP_LATEST)
                .doOnNext(data -> log.info("# emitted by Buffer : {}", data))
                .publishOn(Schedulers.parallel(), false, 1)
                .subscribe(data -> {
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            log.info("onNext : " + data);
                },
                        error -> log.info("error : " + error));
        Thread.sleep(3000L);
    }
}
