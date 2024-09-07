package com.ekici.security.jwt_token.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.key}")
    private String SECRET;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();//payload
        claims.put("ekici", "test");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))//2 min
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean validateToken(String token, UserDetails userDetails) {
        //payload i extract etmeliyiz expireDate'e bakilacak
        String username = extractUsername(token);
        Date expirationDate = extractExpiration(token);

        return userDetails.getUsername().equals(username) && expirationDate.after(new Date());
    }

    public Date extractExpiration(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();

        return claims.getExpiration();
    }

    public String extractUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();

        return claims.getSubject();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);//algoritma da verilabilir
    }
}
