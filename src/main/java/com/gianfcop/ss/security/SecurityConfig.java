package com.gianfcop.ss.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  

  /* 

  @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
          .csrf().disable()
             .authorizeHttpRequests((authorizeHttpRequests) ->
                 authorizeHttpRequests
                     .requestMatchers("/**").permitAll()
             )
             .formLogin();
         return http.build();
     }
     */

   @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET, "/strutture/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/strutture/insert").permitAll()
            .anyRequest().authenticated())
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

    return http.build();

  }
   

}