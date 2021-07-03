package com.example.gateway.api.gateway.security;

import com.example.gateway.api.gateway.filters.JwtTokenAuthenticationFilter;
import com.example.gateway.api.gateway.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Collections;

@Configuration
public class WebSecurityConfiguration {

    @Bean
    SecurityWebFilterChain webFilterChain(ServerHttpSecurity httpSecurity, ReactiveAuthenticationManager authenticationManager, JwtUtil jwtUtil){
        return httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authenticationManager(authenticationManager)
                .authorizeExchange(it -> it
                        .pathMatchers(HttpMethod.POST,"/auth/token").permitAll()
                        .pathMatchers("/**").authenticated()
                        .anyExchange().permitAll()
                )
                .addFilterAt(new JwtTokenAuthenticationFilter(jwtUtil), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository userRepository){
        return username -> userRepository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities(Collections.singletonList("USER").toArray(new String[0]))
                        .accountExpired(false)
                        .accountLocked(false)
                        .disabled(false)
                        .build()
                );
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService reactiveUserDetailsService){
        return new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
    }
}
