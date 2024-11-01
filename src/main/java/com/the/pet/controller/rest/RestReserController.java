package com.the.pet.controller.rest;

import com.the.pet.model.entity.PetEntity;
import com.the.pet.model.entity.ReserEntity;
import com.the.pet.repository.PetRepository;
import com.the.pet.repository.ReserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestReserController {
    @Autowired
    private ReserRepository reserRepository;

    @Autowired
    private PetRepository petRepository;



    @GetMapping("/api/reservations/monthly")
    public ResponseEntity<?> getMonthlyReservations(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {

        System.out.println("Authorization 헤더: " + authorization);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("권한이 없습니다.");
        }
        System.out.println("year : " + year + " month : " + month);

        // 특정 연도와 월에 해당하는 예약 조회
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<ReserEntity> reservations = reserRepository.findAllByReserDateBetween(startDate, endDate, Sort.by("reserTime"));

        // 날짜별 예약 정보를 저장할 맵 생성
        Map<LocalDate, List<ReserEntity>> response = new HashMap<>();

        for (ReserEntity reservation : reservations) {
            LocalDate date = reservation.getReserDate();
            response.putIfAbsent(date, new ArrayList<>());
            response.get(date).add(reservation);
        }

        System.out.println("예약 데이터: " + response);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/api/reservations/daily")
    public ResponseEntity<?> getMonthlyReservations(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam("date") LocalDate date) {

        System.out.println("Authorization 헤더: " + authorization);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("권한이 없습니다.");
        }

        List<ReserEntity> reser  =reserRepository.findByReserDateOrderByReserTimeAsc(date);
        System.out.println("reser =  ----------"+reser);
        return ResponseEntity.ok(reser);
    }



    @GetMapping("/api/checkpet")
    public ResponseEntity<List<PetEntity>> checkPet(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "search", required = false) String search) {

        List<PetEntity> pets;

        if (search != null && search.matches("\\d+")) {
            pets = petRepository.findByOwnerIdLike(search);
        } else {
            pets = petRepository.findByPetNameContainingIgnoreCase(search);
        }

        return ResponseEntity.ok(pets);
    }


    @PostMapping("/api/reseradd")
    public ResponseEntity<?> reseradd(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody ReserEntity reserEntity){
        reserRepository.save(reserEntity);
        return ResponseEntity.ok("등록되었습니다.");
    }





    @PostMapping("/api/reservation/update")
    public ResponseEntity<?> reserupdate(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody ReserEntity reserEntity){

        System.out.println("reserEntity  =   "+reserEntity);
        reserRepository.save(reserEntity);
        return ResponseEntity.ok("수정되었습니다.");
    }



    @PostMapping("/api/reservation/delete")
    public ResponseEntity<?> reserdelete(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value ="reserId") long reserId){
        System.out.println("reserid  =   "+reserId);
        reserRepository.deleteById((int) reserId);
        return ResponseEntity.ok("삭제되었습니다.");
    }




}
