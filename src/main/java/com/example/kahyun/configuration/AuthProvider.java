package com.example.kahyun.configuration;

import com.example.kahyun.VO.LoginVO;
import com.example.kahyun.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AuthProvider implements ReactiveAuthenticationManager {

    @Autowired
    private LoginService loginService;



    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        String userId = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        log.info("Authenticating user: {}", userId);

        return loginService.findByUserId(userId)
                .switchIfEmpty(Mono.error(new BadCredentialsException("사용자를 찾을 수 없습니다.")))
                .flatMap(loginVO -> {
                    log.info("Found user: {}", loginVO);
                    log.info("Provided password: {}", password);
                    log.info("Stored password: {}", loginVO.getPassword());

                    if (passwordEncoder.matches(password, loginVO.getPassword())) {
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        if ("ADMIN".equals(loginVO.getAuth())) {
                            authorities.add(new SimpleGrantedAuthority("ADMIN"));
                        } else {
                            authorities.add(new SimpleGrantedAuthority("USER"));
                        }

                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginVO.getUserId(), loginVO.getUsername(), authorities);
                        log.info("Authentication token created: {}", token);
                        return Mono.just(token);
                    } else {
                        log.warn("비밀번호가 잘못되었습니다.");
                        return Mono.error(new BadCredentialsException("비밀번호가 잘못되었습니다."));
                    }
                });
    }

    /*System.out.println(userId + " " + password);

        PasswordEncoder passwordEncoder = loginService.passwordEncoder();
        *//*UsernamePasswordAuthenticationToken token;*//*

        LoginVO Vo = loginService.findByUserId(userId).block();
        System.out.println("vo : " + Vo);
        if(Vo != null && passwordEncoder.matches(password, Vo.getPassword())) {
            // 사용자 조회 및 비밀번호 검증
            return loginService.findByUserId(userId)
                    .switchIfEmpty(Mono.error(new BadCredentialsException("사용자를 찾을 수 없습니다.")))
                    .flatMap(loginVO -> {

                        // 권한 설정
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        if ("ADMIN".equals(loginVO.getAuth())) {
                            authorities.add(new SimpleGrantedAuthority("ADMIN"));
                        } else {
                            authorities.add(new SimpleGrantedAuthority("USER"));
                        }

                        // 인증 토큰 생성
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(Vo.getUsername(), null, authorities);
                        System.out.println("토큰 : "+token);
                        return Mono.just(token);
                    });
                    throw new BadCredentialsException("잘못됨");
                    */

}
