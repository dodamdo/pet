package com.the.pet.service;

import com.the.pet.model.entity.ReserEntity;
import com.the.pet.repository.ReserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private final ReserRepository reservationRepository;
    private final KakaoMessageService kakaoMessageService;

    public NotificationService(ReserRepository reservationRepository, KakaoMessageService kakaoMessageService) {
        this.reservationRepository = reservationRepository;
        this.kakaoMessageService = kakaoMessageService;
    }

    public void sendNotificationsForTomorrowReservations() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<ReserEntity> reservations = reservationRepository.findReservationsForTomorrow(tomorrow);

        for (ReserEntity reservation : reservations) {
            Map<String, String> templateArgs = new HashMap<>();
            String ownerId  = String.valueOf(reservation.getOwnerId());
            ownerId= "010-" + ownerId.substring(0, 4) + "-" + ownerId.substring(4);
            System.out.println("번호 = = = = = "+ownerId);
            System.out.println("예약 = = = = = "+reservation.toString());
            templateArgs.put("name", reservation.getPetName());
            templateArgs.put("date", reservation.getReserDate().toString());
            templateArgs.put("time", reservation.getReserTime());

            // 실제 전화번호와 템플릿 코드로 발송 (예시: `reservation.getOwnerPhoneNumber()`)
            kakaoMessageService.sendAlimTalk(ownerId, "113734", templateArgs);
        }
    }
}
