package com.the.pet.controller;

import com.the.pet.model.entity.SchEntity;
import com.the.pet.repository.PetRepository;
import com.the.pet.repository.SchRepository;
import com.the.pet.service.SchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class SchController {
    @Autowired
    private SchService schService;

    @Autowired
    private SchRepository schRepository;
    @Autowired
    private PetRepository petRepository;

    @GetMapping("/schedule/schList")
    public String schlist(Model model){
        List<SchEntity> schList = schService.getAllSch();
        for (SchEntity sch : schList) {
            String petName=petRepository.findPetNameById(sch.getPetId());
            String ownerId=petRepository.findOwnerIdById(sch.getPetId());
            if (ownerId.length() == 8) {
                ownerId= "010-" + ownerId.substring(0, 4) + "-" + ownerId.substring(4);
            }
            sch.setPetName(petName);
            sch.setOwnerId(ownerId);
        }


        model.addAttribute("schList",schList);
        return "schedule/schList";

    }


    @GetMapping("/schedule/schMonth")
    public String getschMonth(
            @RequestParam int year,
            @RequestParam int month,Model model) {
        LocalDate currentDate = LocalDate.of(year, month, 1);


        int previousMonth =month-1;
        int nextMonth =month+1;
        int previousYear=year;
        int nextYear =year;
        if(month ==12){
            nextYear=year+1;
            nextMonth=1;
        }else if (month == 1){
            previousYear=year-1;
            previousMonth=12;
        }

        List<SchEntity> schList = schService.getSchedulesForNextMonth(year, month);;
        for (SchEntity sch : schList) {
            String petName=petRepository.findPetNameById(sch.getPetId());
            String ownerId=petRepository.findOwnerIdById(sch.getPetId());
            if (ownerId.length() == 8) {
                ownerId= "010-" + ownerId.substring(0, 4) + "-" + ownerId.substring(4);
            }
            sch.setPetName(petName);
            sch.setOwnerId(ownerId);
        }

        model.addAttribute("schList",schList);
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        model.addAttribute("previousMonth",previousMonth);
        model.addAttribute("nextMonth",nextMonth );
        model.addAttribute("previousYear", previousYear);
        model.addAttribute("nextYear",nextYear);
        System.out.println(schList);

        model.addAttribute("card_total", schService.getTotalPriceForCardPayments("카드", year, month));
        model.addAttribute("cash_total", schService.getTotalPriceForCardPayments("현금", year, month));
        model.addAttribute("account_total", schService.getTotalPriceForCardPayments("이체", year, month));

        return "schedule/schMonth";
    }


    @GetMapping("/calendar")
    public String calender( @RequestParam(required = false) LocalDate schDate,Model model){
        if (schDate == null) {
            schDate = LocalDate.now();
        }

        model.addAttribute("schDate",schDate);
        model.addAttribute("currentYear", LocalDate.now().getYear());
        model.addAttribute("currentMonth", LocalDate.now().getMonthValue() - 1);
        return "calendar";
    }




    @GetMapping("/check_sch")
    @ResponseBody
    public List<SchEntity> checkSchedule(@RequestParam String date) {
        LocalDate schDate = LocalDate.parse(date);


        System.out.println(date);
        System.out.println(schRepository.findBySchDateOrderBySchTimeAsc(schDate));
        List<SchEntity> schList =schRepository.findBySchDateOrderBySchTimeAsc(schDate);
        for (SchEntity sch : schList) {
            String petName=petRepository.findPetNameById(sch.getPetId());
            String ownerId=petRepository.findOwnerIdById(sch.getPetId());
            if (ownerId.length() == 8) {
                ownerId= "010-" + ownerId.substring(0, 4) + "-" + ownerId.substring(4);
            }
            sch.setPetName(petName);
            sch.setOwnerId(ownerId);
        }


        return schList; // 일정 리스트 반환
    }





    @PostMapping("/calendar")
    public SchEntity addSchedule(@ModelAttribute  SchEntity schentity) {
        return schRepository.save(schentity);
    }





    @GetMapping("/outcome")
    public String outcome(){
        return "outcome";
    }
    @GetMapping("/calculate")
    @ResponseBody
    public Map<String, Integer> calculate(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr){
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


    @GetMapping("/schedule/schUpdate")
    public String getScheduleUpdate(@RequestParam("schId") Long schId, Model model) {

        SchEntity schedule = schRepository.findBySchId(schId);
        model.addAttribute("schedule", schedule);
        System.out.println(schedule);
        return "schedule/schUpdate";
    }


    @PostMapping("/schedule/schUpdate")
    public String schupdate(@ModelAttribute  SchEntity schentity) {
        schRepository.save(schentity);
        return "redirect:/schedule/schList";
    }

    @PostMapping("/schedule/schDelete")
    public String deleteSchedule(@RequestParam Integer schId) {
        schRepository.deleteById(schId);
        return "redirect:/schedule/schList";
    }


    @GetMapping("/schedule/schAdd")
    public String schAdd( ){
        return "schedule/schAdd";
    }

    @PostMapping("/schedule/schAdd")
    public String schAddDB(@ModelAttribute  SchEntity schentity) {
        schRepository.save(schentity);
        return "redirect:/calendar?schDate=" + schentity.getSchDate();
    }


    @GetMapping("/schedule/petschinfo")
    @ResponseBody
    public List<SchEntity> petschinfo(
            @RequestParam(value = "petId", required = false) Integer petId, Model model) {

        List<SchEntity> schList = schRepository.findByPetIdOrderBySchDateDesc(petId);

        // 최근 3개 예약 내역만 가져오기
        if (schList.size() > 3) {
            schList = schList.subList(schList.size() - 3, schList.size());
        }

        for (SchEntity sch : schList) {
            String petName = petRepository.findPetNameById(sch.getPetId());
            String ownerId = petRepository.findOwnerIdById(sch.getPetId());
            if (ownerId.length() == 8) {
                ownerId = "010-" + ownerId.substring(0, 4) + "-" + ownerId.substring(4);
            }
            sch.setPetName(petName);
            sch.setOwnerId(ownerId);
        }

        return schList;
    }

    @PostMapping("/schedule/schmemoupdate")
    public String schmemoupdate(@RequestParam Integer schId, @RequestParam String schNotes,
                                @RequestParam(value = "petId") Integer petId) {
        SchEntity schedule = schRepository.findById(schId).orElse(null);
        schedule.setSchNotes(schNotes);
        schRepository.save(schedule);
        return "redirect:/pets/petDetail?petId="+petId;
    }





}
