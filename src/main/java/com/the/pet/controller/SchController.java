package com.the.pet.controller;

import com.the.pet.model.entity.OwnerEntity;
import com.the.pet.model.entity.PetEntity;
import com.the.pet.model.entity.SchEntity;
import com.the.pet.repository.PetRepository;
import com.the.pet.repository.SchRepository;
import com.the.pet.service.PetService;
import com.the.pet.service.SchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
@Slf4j
public class SchController {
    @Autowired
    private SchService schService;

    @Autowired
    private SchRepository schRepository;

    @GetMapping("/schedule/schList")
    public String schlist(Model model){
        List<SchEntity> schList = schService.getAllSch();
        model.addAttribute("schList",schList);
        return "schedule/schList";

    }

    @GetMapping("/schedule/schDetail")
    public String getSchedulesForNextMonth(
            @RequestParam int year,
            @RequestParam int month,Model model) {
        LocalDate currentDate = LocalDate.of(year, month, 1);

        int previousMonth=month-1;
        int nextMonth=month+1;
        int previousYear=year;
        int nextYear=year;
        if(month ==12){
            nextYear=year++;
            nextMonth=1;
        }else if (month == 1){
            nextYear=year--;
            nextMonth=12;
        }

        List<SchEntity> schList = schService.getSchedulesForNextMonth(year, month);;


        model.addAttribute("schList",schList);
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        model.addAttribute("previousMonth",previousMonth);
        model.addAttribute("nextMonth",nextMonth );
        model.addAttribute("previousYear", previousYear);
        model.addAttribute("nextYear",nextYear);
        System.out.println(schList);
        return "schedule/schDetail";
    }



}
