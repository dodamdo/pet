package com.the.pet.controller;

import com.the.pet.model.entity.SchEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Controller
@Slf4j
public class calendarController {

    @GetMapping("/priceList")
    public String showPriceTable() {
        return "priceList";
    }



}
