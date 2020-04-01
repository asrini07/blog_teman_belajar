package com.example.temanbelajar.config;

import com.example.temanbelajar.exeption.AuthExceptionHandler;
import com.example.temanbelajar.exeption.CustomAccessDeniedHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;


/**
 * ResourceServerConfig
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
            .headers()
                .frameOptions()
                .disable()
                .and()
            .authorizeRequests()
                .antMatchers("/authors/**","/blogs/**","/categories/**","/tags/**").permitAll()
                // .antMatchers("/blogs").authenticated()
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthExceptionHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }
}