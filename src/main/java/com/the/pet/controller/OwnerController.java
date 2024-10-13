package com.the.pet.controller;


import com.the.pet.model.entity.OwnerEntity;
import com.the.pet.service.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
public class OwnerController {

    @Autowired
    private OwnerService ownerService;


    @GetMapping("/owners/ownerList")
    public String listOwners(Model model) {
        List<OwnerEntity> ownerList = ownerService.getAllOwners();
        System.out.println(ownerList);
        model.addAttribute("ownerList", ownerList);
        return "/owners/ownerList";
    }


}
