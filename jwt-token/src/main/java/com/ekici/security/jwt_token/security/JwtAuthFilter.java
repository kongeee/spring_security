package com.ekici.security.jwt_token.security;

import com.ekici.security.jwt_token.service.JwtService;
import com.ekici.security.jwt_token.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    private static final String BEARER_KEY = "Bearer ";//bosluuk olmali

    public JwtAuthFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    //request icindeki tokeni validate eder
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith(BEARER_KEY)) {
            token = authHeader.substring(BEARER_KEY.length());
            username = jwtService.extractUsername(token);

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {//mecvut thread in contectinde authentication olmamali
            UserDetails user = userService.loadUserByUsername(username);

            if (jwtService.validateToken(token, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());//credential password e denk gelir. boos verebiliriz jtw parse edince password gorunmesin diye
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //auth bilgisi context e gonderilir
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);//token validationdan sonra filter islemlerine devam etmek icin
    }
}
