package com.mrattcs.instagramapi.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());

            String jwt = Jwts.builder()
                    .setIssuer("Instagram")
                    .setIssuedAt(new Date())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .claim("username", authentication.getName())
                    .setExpiration(new Date(new Date().getTime() + 300000000))
                    .signWith(key).compact();

            response.setHeader(SecurityContext.HEADER, jwt);
        }
        filterChain.doFilter(request, response);
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authorities = new HashSet<>();

        for (GrantedAuthority g : collection) {
            authorities.add(g.getAuthority());
        }
        return String.join(",", authorities);
    }

    protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException {
        return !req.getServletPath().equals("/signin");
    }
}
