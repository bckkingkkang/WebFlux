package com.example.kahyun.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.Calendar;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    @RequestMapping("/main")
    public Mono<String> main() {

        // ReactiveSecurityContextHolder.getContext() : 현재 스레드에 연결된 보안 컨텍스트를 비동기 방식으로 가져옴
        return ReactiveSecurityContextHolder.getContext()
                // doOnNext : 요소 소비 시 실행되는 콜백 등록
                .doOnNext(securityContext -> {
                    // 보안 컨텍스트에서 인증 정보를 가져옴
                    Authentication authentication = securityContext.getAuthentication();
                    // 인증 정보에서 사용자 아이디 추출
                    String userId = (String) authentication.getPrincipal();
                    // 사용자 아이디, 인증 정보 출력
                    System.out.println("로그인된 유저 : " + userId);
                    System.out.println("authentication : "+authentication);

                    /*Calendar now = Calendar.getInstance();
                    int year = now.get(Calendar.YEAR);
                    int month = now.get(Calendar.MONTH);
                    int day = now.get(Calendar.DAY_OF_MONTH);
                    int week = now.get(Calendar.DAY_OF_WEEK);
                    int hour = now.get(Calendar.HOUR_OF_DAY);
                    int minute = now.get(Calendar.MINUTE);
                    int second = now.get(Calendar.SECOND);
                    System.out.println("날짜" +year + month + day + week + hour + minute + second);*/
                })
                // "/main" 뷰 반환
                .then(Mono.just("/main"));
    }

    @GetMapping("/detail/{seq}")
    public Mono<String> detail(@PathVariable int seq) {
        return Mono.just("detail" + seq);
    }
}
