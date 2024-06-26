package com.example.kahyun.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
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
                        .authenticationManager(authProvider)
                        .requiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/user/loginForm"))
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new RedirectServerLogoutSuccessHandler())
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }

}
