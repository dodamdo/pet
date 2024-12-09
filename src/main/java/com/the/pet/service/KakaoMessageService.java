package com.the.pet.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoMessageService {

    private final WebClient webClient;

    public KakaoMessageService(
            @Value("${kakao.api.url}") String kakaoApiUrl,
            @Value("${kakao.api.admin-key}") String adminKey
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(kakaoApiUrl)
                .defaultHeader("Authorization", "KakaoAK " + adminKey)
                .build();
    }

    public void sendAlimTalk(String phoneNumber, String templateCode, Map<String, String> templateArgs) {
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("phone_number", phoneNumber);
        requestPayload.put("template_code", templateCode);        requestPayload.put("template_args", templateArgs);

        webClient.post()
                .uri("/alimtalk/send")
                .bodyValue(requestPayload)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> System.err.println("알림톡 발송 실패: " + e.getMessage()))
                .subscribe(response -> System.out.println("알림톡 발송 성공: " + response));
    }
}
