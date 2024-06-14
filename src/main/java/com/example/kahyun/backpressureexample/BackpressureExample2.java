package com.example.kahyun.backpressureexample;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

// Subscriber가 처리 가능한 만큼의 request 갯수를 조절하는 Backpressure 예제

@Slf4j
public class BackpressureExample2 {
    public static int count = 0;
    public static void main(String[] args) throws InterruptedException {

        Flux.range(1, 5)
                .doOnNext(value -> System.out.println("doOnNext : " + value))
                .doOnRequest(value -> System.out.println("doOnRequest : "+value))

                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(2); // 데이터의 개수 2로 지정
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        count++;    // 데이터 전달마다 count 증가
                        System.out.println(value);
                        if(count == 2) {
                            try {
                                Thread.sleep(2000L);    // count 값이 2인 경우 2초 지연
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            request(2);
                            count = 0;
                        }
                    }
                });
    }
}
