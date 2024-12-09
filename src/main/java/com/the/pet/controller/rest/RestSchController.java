package com.the.pet.controller.rest;

import com.the.pet.model.entity.PetEntity;
import com.the.pet.model.entity.SchEntity;
import com.the.pet.repository.PetRepository;
import com.the.pet.repository.SchRepository;
import com.the.pet.service.SchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestSchController {
    @Autowired
    private SchRepository schRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private SchService schService;

    @GetMapping("/api/reservation")
    public ResponseEntity<?> getReservations(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam LocalDate schDate ) {

        System.out.println("Authorization 헤더: " + authorization);

        //List<SchEntity> schedules = schRepository.findSchedulesBetween(startDate, nextMonthStart);
        List<SchEntity> schedules =schRepository.findBySchDateOrderBySchTimeAsc(schDate);
        for (SchEntity schedule : schedules) {
            PetEntity pet = petRepository.findById(Math.toIntExact(schedule.getPetId()))
                    .orElse(null);

            if (pet != null) {
                schedule.setPetName(pet.getPetName());

                String ownerId = String.valueOf(pet.getOwnerId());
                if (ownerId.length() == 8) {
                    ownerId = "010-" + ownerId.substring(0, 4) + "-" + ownerId.substring(4);
                }
                schedule.setOwnerId(ownerId);
            }
        }

        System.out.println("스케줄은 - - -" + schedules);
        return ResponseEntity.ok(schedules);
    }

    @PostMapping("/api/changecolor")
    public ResponseEntity<?> changeScheduleColor(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam String schColor,
            @RequestParam Long schId){
        System.out.println("Authorization 헤더: " + authorization);
        System.out.println("색상변경 " + schColor);
        SchEntity schedule = schRepository.findById(Math.toIntExact(schId)).orElse(null);
        schedule.setSchColor(schColor);
        schRepository.save(schedule);
        return ResponseEntity.ok("색상이 변경되었습니다.");

    }



    @GetMapping("/api/calculate")
    @ResponseBody
    public Map<String, Integer> calculate(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        int cardTotal = schService.getTotalOutcomeForCardPayments("카드", startDate, endDate);
        int cashTotal = schService.getTotalOutcomeForCardPayments("현금", startDate, endDate);
        int accountTotal = schService.getTotalOutcomeForCardPayments("이체", startDate, endDate);

        Map<String, Integer> result = new HashMap<>();
        result.put("card_total", cardTotal);
        result.put("cash_total", cashTotal);
        result.put("account_total", accountTotal);
        result.put("total_amount", cardTotal + cashTotal + accountTotal);

        return result;
    }

    @PostMapping("/api/schmemoupdate")
    public ResponseEntity<?> updateScheduleMemo(
            @RequestBody Map<String, Object> requestBody) {
        try {
            Integer schId = (Integer) requestBody.get("schId");
            String schNotes = (String) requestBody.get("schNotes");

            SchEntity schedule = schRepository.findById(schId).orElseThrow(() ->
                    new RuntimeException("스케줄을 찾을 수 없습니다: ID = " + schId)
            );
            schedule.setSchNotes(schNotes);
            schRepository.save(schedule);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "스케줄 메모가 성공적으로 업데이트되었습니다.");
            response.put("updatedSchedule", schedule);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "스케줄 메모 업데이트 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }




}
