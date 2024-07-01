package com.example.kahyun.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Slf4j
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    // 비밀번호 인코딩
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthProvider authProvider) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authProvider);
        authenticationWebFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/user/loginForm"));
        authenticationWebFilter.setAuthenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/main"));
        authenticationWebFilter.setAuthenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/user/login"));

        return http
                .authorizeExchange(exchanges -> exchanges
                        // 특정 경로 인증 없이 접근 허용
                        .pathMatchers("/user/**", "/mail/**", "/signup/**", "/main").permitAll()
                        // 그 외 모든 요청 인증 필요
                        .anyExchange().authenticated()
                )
                .formLogin(login -> login
                        // 로그인 페이지 설정
                        .loginPage("/user/login")
                        // 로그인 성공 핸들러 설정
                        .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/main"))
                        // 로그인 실패 핸들러 설정
                        .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/user/login"))
                        // 인증 관리자 설정
                        .authenticationManager(authProvider)
                        // 인증 경로
                        .requiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/user/loginForm"))
                )
                .logout(logout -> logout
                        // 로그아웃 URL
                        .logoutUrl("/user/logout")
                )
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                // 보안 컨텍스트 리포지토리 설정
                .securityContextRepository(securityContextRepository())
                .build();
    }


    // security context repository 설정
    @Bean
    public ServerSecurityContextRepository securityContextRepository() {
        // 세션 기반 security context repository 반환
        return new WebSessionServerSecurityContextRepository();
    }


}
