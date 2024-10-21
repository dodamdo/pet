package com.the.pet.controller.rest;

import com.the.pet.model.entity.PetEntity;
import com.the.pet.repository.PetRepository;
import com.the.pet.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class AuthController {

    @Autowired
    private AuthService authService;

    private String fixedUsername = "user";
    private String encodedPassword = "$2a$10$TOMEogdfqpw1cLJf27AoDOMo0B8i6M.P4wXEhsqjBpd.8S6cEupAm";




    @PostMapping("/flutterLogin")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request, HttpServletRequest req) {
        String username = request.get("username");
        String password = request.get("password");

        // 사용자 인증 로직
        if (fixedUsername.equals(username) && authService.matches(password, encodedPassword)) {
            // 인증 성공, 세션에 사용자 정보 저장
            req.getSession().setAttribute("username", username);

            // JSON 응답으로 반환
            Map<String, String> response = new HashMap<>();
            response.put("message", "로그인 성공");

            req.getSession().setMaxInactiveInterval(24 * 60 * 60);

            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "인증 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }


}