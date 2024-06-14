package com.example.kahyun.backpressureexample;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/*
    Subscriber가 처리 가능한 만큼의 request 개수를 조절하는 예제
*/

@Slf4j
public class BackpressureExample1  {
    public static void main(String[] args) throws InterruptedException {

        Flux.range(1, 5)    // 1부터 5까지의 정수를 발행하는 Flux 생성
                .doOnNext(value -> System.out.println("doOnNext (요청 개수): " + value))    // Flux에서 각 항목이 발행될 때 실행
                .doOnRequest(n -> System.out.println("doOnRequest(요청한 데이터의 갯수, 데이터 emit): " + n))      // 구독자가 데이터를 요청할 때 실행
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        // hookOnSubscribe -> 데이터 요청 개수 1로 지정
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("onNext(전달받은 데이터 출력): " + value);
                        request(1);
                    }
                });
        Thread.sleep(10000);
    }
}
