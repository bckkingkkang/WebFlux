package com.example.kahyun.backpressureexample;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/*
    Unbounded request 일 경우, Downstream 에 Backpressure LATEST 전략을 적용
    - Downstream 으로 전달 할 데이터가 Buffer에 가득 찰 경우,
      Buffer 밖에서 폐기되지 않고 대기하는 가장 나중(최근)에 emit 된 데이터부터 Buffer 에 채운다.
*/

@Slf4j
public class BackpressureLatestExample {
    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureLatest()
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            try {
                                Thread.sleep(5L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            log.info("onNext : " + data);
                },
                        error -> log.info("error : " + error));

        Thread.sleep(2000L);
    }
}
