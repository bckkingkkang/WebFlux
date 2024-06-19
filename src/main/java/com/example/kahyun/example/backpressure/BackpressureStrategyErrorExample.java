package com.example.kahyun.example.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/*
    Unbounded request 인 경우, Downstream 에 Backpressure ERROR 전략을 적용

    - Downstream 으로 전달할 데이터가 Buffer 에 가득 찬 경우, Exception 발생
*/

@Slf4j
public class BackpressureStrategyErrorExample {
    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(1L))    // 일정한 간격으로 데이터 생성 emit
                .onBackpressureError()              // error 전략 적용
                .doOnNext(value -> System.out.println("doOnNext (emit된 데이터 출력) : " + value))    // emit된 데이터 출력
                .publishOn(Schedulers.parallel())   // 추가적으로 Thread 를 할당해서 작업을 할 수 있게 해줌 (Schedulers)
                .subscribe(data -> {
                            try {
                                Thread.sleep(5L);   // publisher에서 emit하는 속도보다 subscriber에서 처리하는 속도가 느리도록
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("onNext (처리된 데이터 출력) : " + data);
                    },
                        error -> System.out.println("error : " + error));

        Thread.sleep(2000L);
    }
}
