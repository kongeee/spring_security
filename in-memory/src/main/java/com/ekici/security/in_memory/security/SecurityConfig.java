package com.ekici.security.in_memory.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity //direkt path degil controller sinifini verirsek oradaki metodlarin securitysini saglar
public class SecurityConfig {

    //Encryption
    //BCryptPasswordEncoder -> Base64
    //PasswordEncoder => SHA-1
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users() {
        //in memory e user eklemek, authlari almak vs. icin bu servisi kullancagiz
        UserDetails user1 = User.builder()
                .username("furkan")
                .password(passwordEncoder().encode("pass"))
                .roles("USER") //ROLE_ ile baslama zaten kendisi otomatik koyuyor
                .build();

        UserDetails user2 = User.builder()
                .username("busra")
                .password(passwordEncoder().encode("pass"))
                .roles("ADMIN") //ROLE_ ile baslama zaten kendisi otomatik koyuyor
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))//custom headerlari handle eder
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)//iptal edersek headerdan gelen bilgileri kullanir, enable edilirse default login form olusturur (Customizer.withDefaults())
                .authorizeHttpRequests(//api bazinda security kontrolu
                        x -> x.requestMatchers("/public/**", "/auth**").permitAll() //secuurity uygulanmak istenen endpoint patternleri (permisAll  => herkese izin ver | authenticated -> authenticate olmasi yeterli | hasRole -> sadece bu role izin ver | hasAnyRole ->  bu rollerden herhangi biri varsa)
                )
                .authorizeHttpRequests(x -> x.requestMatchers("/private/user/**").hasRole("USER")) //bunun yerine apide PreAuthorize kullanilabilir
                .authorizeHttpRequests(x -> x.requestMatchers("/private/admin/**").hasRole("ADMIN"))//bunun yerine apide PreAuthorize kullanilabilir
                //alt alta requestMatchers yazmaktansa izin durumlarina gore boyle grupla
                //authorizeHttpRequests siralamalari onemlidir bunu en uste alsaydik ve birisi auth a gelmek isteseydi authenticate olmadigi ve bu ilk calisacagi icin auth servisine gelemezdi
                //genelde anyRequest authenticated en altta olur
                .authorizeHttpRequests(x -> x.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return security.build();

    }
}
