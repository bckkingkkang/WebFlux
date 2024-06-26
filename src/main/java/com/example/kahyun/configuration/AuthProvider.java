package com.example.kahyun.configuration;

import com.example.kahyun.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class AuthProvider implements ReactiveAuthenticationManager {

    @Autowired
    private LoginService loginService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        String password = authentication.getCredentials().toString();
        System.out.println(userId + ", " + password);

        return loginService.findByUserId(userId)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .map(userDetails -> {
                    if (passwordEncoder.matches(password, userDetails.getPassword())) {
                        log.info(String.valueOf(userDetails));
                        return new UsernamePasswordAuthenticationToken(userId, password, List.of());
                    } else {
                        throw new BadCredentialsException("Invalid Credentials");
                    }
                });
    }
}
