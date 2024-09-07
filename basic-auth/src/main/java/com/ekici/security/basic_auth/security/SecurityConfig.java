package com.ekici.security.basic_auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        //h2Console hatasi verirse
        //https://www.youtube.com/watch?v=JdnwMpP6YhE 2:22:00
        security
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x ->
                    x
                        .requestMatchers("/public/**").permitAll()
                            .requestMatchers("/private/user/**").hasAnyRole("USER", "ADMIN") //spesifik olan daha yukarda
                            .requestMatchers("/private/**").hasRole("ADMIN")
                            .anyRequest().authenticated()

                )
                //session icin formlogin olmali session timeout olunca /logout endpointine otomatik yonelndiriyor
                .formLogin(Customizer.withDefaults())//AbstractHttpConfigurer::disable
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))//session management -> properties dosyasinda sure var
                ;

        return security.build();
    }
}
