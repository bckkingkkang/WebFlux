package com.example.kahyun.example.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;
import java.util.concurrent.TimeUnit;


@Slf4j
public class HotSequence {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> concertFlux =
                Flux.fromStream(Stream.of("A","B","C","D","E"))
                        .delayElements(Duration.ofSeconds(1)).share();
        // share -> 원본 Flux를 여러 subscriber가 공유하도록 한다. (cold sequence를 hot sequence로 변환해주는 operator)

        concertFlux.subscribe(code -> log.info("# Subscriber {}", code));

        TimeUnit.SECONDS.sleep(3);

        concertFlux.subscribe(code -> log.info("# Subscriber 2 {}", code));

        TimeUnit.SECONDS.sleep(4);
    }
}
