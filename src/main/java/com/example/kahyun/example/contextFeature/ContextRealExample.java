package com.example.kahyun.example.contextFeature;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

// Context 활용 예제 - 직교성을 가지는 정보를 표현할 때 주로 사용된다.
// 직교성 : 애플리케이션 실행에 영향을 주지 않는 정보

@Slf4j
public class ContextRealExample {

    public static final String HEADER_NAME_AUTH_TOKEN = "authToken";

    public static void main(String[] args) {
        Mono<String> mono
                = postBook(Mono.just(new Book("abcd-1111-3533-2809", "Reactor's Bible", "kahyun")))
                // 토큰 저장
                .contextWrite(Context.of(HEADER_NAME_AUTH_TOKEN, "eyadfaksdlfadsfdsfasdaf"));

        mono.subscribe(data -> log.info(data));
    }

    private static Mono<String> postBook(Mono<Book> book) {
        return Mono.zip(book, Mono.deferContextual(ctx -> Mono.just(ctx.get(HEADER_NAME_AUTH_TOKEN))))
                // 외부 API 서버로 HTTP Post request 를 전송한다고 가정
                .flatMap(tuple -> Mono.just(tuple))

                .flatMap(tuple -> {
                    String response = "POST the book(" + tuple.getT1().getBookName() + ", " + tuple.getT1().getAuthor() + ") with token : " + tuple.getT2();

                    // HTTP response 를 수신했다고 가정
                    return Mono.just(response);
                });
    }
}
