package com.example.kahyun.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthProvider authProvider) {
        return http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/user/**", "/mail/**", "/signup/**","/main").permitAll()
                        .anyExchange().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/user/login")
                        .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/main"))
                        .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/user/login"))
                        .requiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/user/loginForm"))
                        .authenticationManager(authProvider)
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                )
                .csrf(csrf -> csrf.disable())
                .securityContextRepository(securityContextRepository())

                .build();
    }*/
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthProvider authProvider) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authProvider);
        authenticationWebFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/user/loginForm"));
        authenticationWebFilter.setAuthenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/main"));
        authenticationWebFilter.setAuthenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/user/login"));

        return http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/user/**", "/mail/**", "/signup/**", "/main").permitAll()
                        .anyExchange().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/user/login")
                        .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/main"))
                        .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/user/login"))
                        .authenticationManager(authProvider)
                        .requiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/user/loginForm"))
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                )
                .csrf(csrf -> csrf.disable())
                .securityContextRepository(securityContextRepository())
                .build();
    }



    @Bean
    public ServerSecurityContextRepository securityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }


}
