package com.example.kahyun.configuration;

import com.example.kahyun.handler.FailureHandler;
import com.example.kahyun.handler.SuccessHandler;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf(csrf-> csrf.disable()
                )
                .authorizeExchange(request -> request
                    .pathMatchers("/user/**").permitAll()
                    .anyExchange().authenticated()
                )
                // 로그인 페이지 커스터마이징
                .formLogin(login -> login
                        .loginPage("/user/login")
                        .requiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/user/loginForm"))
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                )
        ;

        return http.build();

    }
}
