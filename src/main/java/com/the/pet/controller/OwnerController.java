package com.the.pet.controller;


import com.the.pet.model.entity.OwnerEntity;
import com.the.pet.repository.OwnerRepository;
import com.the.pet.repository.PetRepository;
import com.the.pet.service.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
public class OwnerController {

    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PetRepository petRepository;


    @GetMapping("/owners/ownerAdd")
    public String ownerAdd(@RequestParam("petId") Long petId, Model model) {
        model.addAttribute("petId",petId);
        return "/owners/ownerAdd";
    }

    @PostMapping("/owners/ownerAdd")
    public String ownerAddDB(@ModelAttribute OwnerEntity owner, Model model) {
        String petName = petRepository.findPetNameById(owner.getPetId());

        owner.setPetName(petName);
        ownerRepository.save(owner);
        System.out.println("33333333333333         " +owner.toString());

        return "redirect:/pets/petDetail?petId="+owner.getPetId();
    }




}
