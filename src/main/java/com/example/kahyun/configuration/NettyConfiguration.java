package com.example.kahyun.configuration;

import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyConfiguration {

    /*
        Netty 사용 이유
        : 비동기 I/O 모델을 기반으로 하는 고성능 서버로서 많은 동시 연결을 효율적으로 처리 가능
          WebFlux 같은 비동기 애플리케이션에서 유용

        HTTP 응답 압축
        : 압축을 활성화하면 클라이언트와 서버 간의 데이터 전송량을 줄일 수 있어 성능을 향상시킬 수 있다.
          압축은 CPU 사용량을 증가시킬 수 있다.
    */

    // Netty 커스터마이징
    @Bean
    public NettyServerCustomizer nettyServerCustomizer() {
        return httpServer -> httpServer
                .port(8080)
                // HTTP 응답 압축 활성화 -> 네트워크 대역폭 절약
                .compress(true);
    }
}
