package com.the.pet.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;
        System.out.println("처음받은 값 --  - - "+authorizationHeader);
        // Authorization 헤더에서 JWT 추출
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }

        System.out.println("토큰 값1 --  - - "+jwt);
        // JWT가 유효한 경우 SecurityContextHolder에 인증 정보를 설정
        if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
            // 인증된 토큰을 SecurityContext에 설정

            System.out.println("토큰 값2 --  - - "+jwt);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(null, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            System.out.println("토큰 값3 --  - - "+jwt);
        }

        chain.doFilter(request, response); // 다음 필터로 요청 전달
    }
}
