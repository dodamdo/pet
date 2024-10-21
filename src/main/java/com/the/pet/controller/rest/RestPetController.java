package com.the.pet.controller.rest;

import com.the.pet.model.entity.PetEntity;
import com.the.pet.repository.PetRepository;
import com.the.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestPetController
{
    @Autowired
    private PetService petService;

    @Autowired
    private  PetRepository petRepository;

    @GetMapping("/selectAll")
    public ResponseEntity<List<PetEntity>> selectAll(){
        List<PetEntity> dtos= petRepository.findAll();
        if(dtos==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }


    @GetMapping("/api/flutterPetSearch")
    public ResponseEntity<List<PetEntity>> getPetSearch(
            @RequestParam(value = "search", required = false) String search) {

        List<PetEntity> pets;
        boolean isNumeric = search != null && search.matches("\\d+"); // 숫자열 확인
        if (isNumeric) {
            pets = petRepository.findByOwnerIdLike(search); // 전화번호 검색
        } else {
            pets = petRepository.findByPetNameContainingIgnoreCase(search); // 펫 이름으로 검색
        }

        for (PetEntity pet : pets) {
            if (pet.getOwnerId().toString().length() == 8) {
                String formattedOwnerId = "010-" + pet.getOwnerId().toString().substring(0, 4) + "-" + pet.getOwnerId().toString().substring(4);
                pet.setFormattedOwnerId(formattedOwnerId);
            }
        }
        System.out.println(pets);
        return ResponseEntity.status(HttpStatus.OK).body(pets);
    }
}
