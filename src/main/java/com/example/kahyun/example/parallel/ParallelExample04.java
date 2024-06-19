package com.example.kahyun.example.parallel;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    parallel() 만 사용할 경우에는 병렬로 작업을 수행하지 않는다.
    runOn()을 사용해서 Scheduler 를 할당해주어야 병렬로 작업을 수행한다.
    CPU 코어 갯수에 의존하지 않고, worker thread 를 강제 할당한다.
*/

@Slf4j
public class ParallelExample04 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19})
                .parallel(4)        // Thread 의 갯수 지정
                .runOn(Schedulers.parallel())
                .subscribe(data -> log.info("{}", data));

        Thread.sleep(100L);
    }
}
