package com.example.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll() // Разрешаем доступ к нашим API без авторизации
                .antMatchers("/h2-console/**").permitAll() // Разрешаем доступ к консоли H2
                .anyRequest().authenticated()
                .and().headers().frameOptions().disable(); // Отключаем защиту iframe для H2 консоли
        return http.build();
    }
}