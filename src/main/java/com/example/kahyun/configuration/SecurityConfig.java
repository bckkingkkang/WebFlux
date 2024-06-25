package com.example.kahyun.configuration;

import com.example.kahyun.handler.FailureHandler;
import com.example.kahyun.handler.SuccessHandler;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

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
                        .pathMatchers("/user/**", "/mail/**", "/signup/**").permitAll()
                        .anyExchange().authenticated()
                )
                .formLogin(formLoginSpec -> formLoginSpec
                        .loginPage("/user/login")
                        .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/main"))
                        .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/user/login?error=true"))
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
