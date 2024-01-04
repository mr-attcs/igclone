package com.mrattcs.instagramapi.security;

import com.mrattcs.instagramapi.config.SecurityContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtTokenProvider {

    public JwtTokenClaims getClaimsFromToken(String token) {

        SecretKey key = Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());
        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();

            String username = String.valueOf(claims.get("username"));

            JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
            jwtTokenClaims.setUsername(username);

            return jwtTokenClaims;
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("Invalid token");
        }
    }
}