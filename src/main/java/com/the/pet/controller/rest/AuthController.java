package com.the.pet.controller.rest;

import com.the.pet.security.JwtTokenProvider;
import com.the.pet.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private String fixedUsername = "user";
    private String encodedPassword = "$2a$10$TOMEogdfqpw1cLJf27AoDOMo0B8i6M.P4wXEhsqjBpd.8S6cEupAm";

    @CrossOrigin(origins = "*")
    @PostMapping("/flutterLogin")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        if (request == null) {
            return ResponseEntity.ok().build();
        }
        String username = request.get("username");
        String password = request.get("password");
        System.out.println("username  ```````11111111111111```"+username);
        System.out.println("password  ```````11111111111111```"+password);
        System.out.println("들어오긴함  ```````11111111111111```");

        if (fixedUsername.equals(username) && authService.matches(password, encodedPassword)) {
            // Authentication 객체 생성
            Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")); // 필요한 권한 설정
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

            // JWT 토큰 생성
            System.out.println("토큰생성전 : ``````````");
            String token = tokenProvider.generateToken(String.valueOf(authentication));
            System.out.println("토큰은 : ``````````"+token);
            Map<String, String> response = new HashMap<>();
            response.put("message", "로그인 성공");
            response.put("token", token);

            return ResponseEntity.ok(response);
        }  else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "인증 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
