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




    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        String userId = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        PasswordEncoder passwordEncoder = loginService.passwordEncoder();
        /*UsernamePasswordAuthenticationToken token;*/

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
        }
        throw new BadCredentialsException("잘못됨");


    }


}
