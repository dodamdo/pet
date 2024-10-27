package com.the.pet.controller;


import com.the.pet.model.entity.OwnerEntity;
import com.the.pet.repository.OwnerRepository;
import com.the.pet.repository.PetRepository;
import com.the.pet.service.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        try {
            List<OwnerEntity> owners = ownerRepository.findByPetId(petId);
            model.addAttribute("petId", petId);

            model.addAttribute("owners", owners);
            System.out.println(owners);
            return "/owners/ownerAdd";
        } catch (Exception e) {
            // 에러를 로깅합니다.
            e.printStackTrace();
            model.addAttribute("errorMessage", "오류가 발생했습니다: " + e.getMessage());
            return "error"; // 에러 페이지로 리다이렉트
        }
    }






    @PostMapping("/deleteOwner")
    public String deleteOwner(@RequestParam Long extraownerId, @RequestParam Long petId) {
        System.out.println("삭제 요청: Extra Owner ID = " + extraownerId + ", Pet ID = " + petId);
        ownerRepository.deleteById(extraownerId);
        return "redirect:/pets/petDetail?petId="+petId;
    }




}
