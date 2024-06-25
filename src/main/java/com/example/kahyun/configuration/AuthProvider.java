package com.example.kahyun.configuration;

import com.example.kahyun.service.LoginService;
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

@Component
public class AuthProvider implements ReactiveAuthenticationManager {

    @Autowired
    private LoginService loginService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        String user_id = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        PasswordEncoder passwordEncoder = loginService.passwordEncoder();

        return loginService.selectUser_id(user_id)
                .flatMap(loginVo -> {
                    if (loginVo != null && passwordEncoder.matches(password, loginVo.getPassword())) {
                        List<GrantedAuthority> roles = new ArrayList<>();

                        if ("ADMIN".equals(loginVo.getAuth())) {
                            roles.add(new SimpleGrantedAuthority("ADMIN"));
                        } else if ("USER".equals(loginVo.getAuth())) {
                            roles.add(new SimpleGrantedAuthority("USER"));
                        }

                        Authentication token = new UsernamePasswordAuthenticationToken(loginVo.get_id(), null, roles);
                        return Mono.just(token);
                    } else {
                        return Mono.error(new BadCredentialsException("아이디나 비밀번호가 잘못됐음"));
                    }
                });
    }


}
