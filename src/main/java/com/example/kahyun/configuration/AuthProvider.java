package com.example.kahyun.configuration;

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

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {

        // username(user id), password 받아옴
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        PasswordEncoder passwordEncoder = loginService.passwordEncoder();

        // 확인
        log.info("Authenticating details : {}", authentication.getDetails());
        log.info("Authenticating principal : {}", authentication.getPrincipal());
        log.info("Authenticating auth : {}", authentication.getAuthorities());
        log.info("Authenticating auth : {}", authentication.isAuthenticated());
        log.info("Authenticating username : {}", username);
        log.info("Authenticating password : {}", password);

        return loginService.findByUserId(username)
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

                        // 인증 token 생성
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginVO.getUserId(), null,  authorities);
                        log.info("Authentication token created: {}", token);
                        return Mono.just(token);
                    } else {
                        log.warn("비밀번호가 잘못되었습니다.");
                        return Mono.error(new BadCredentialsException("비밀번호가 잘못되었습니다."));
                    }
                });
    }

}
