package com.the.pet.controller.rest;

import com.the.pet.model.entity.OwnerEntity;
import com.the.pet.model.entity.PetEntity;
import com.the.pet.model.request.PetInfoDto;
import com.the.pet.model.request.PetOwnerDTO;
import com.the.pet.repository.OwnerRepository;
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
    @Autowired
    private OwnerRepository ownerRepository;


    @GetMapping("/selectAll")
    public ResponseEntity<List<PetInfoDto>> selectAll(){
        System.out.println("selectAll -----------------------");
        List<PetInfoDto> petDetails = petService.getAllPetDetails();
        return ResponseEntity.ok(petDetails);

    }


    @GetMapping("/api/flutterPetSearch")
    public ResponseEntity<PetOwnerDTO> getPetSearch(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "search", required = false) String search) {
        System.out.println("Authorization 헤더: " + authorization);
        List<PetEntity> pets;
        List<OwnerEntity> owners;
        boolean isNumeric = search != null && search.matches("\\d+"); // 숫자열 확인
        if (isNumeric) {
            pets = petRepository.findByOwnerIdLike(search);
            owners = ownerRepository.findByOwnerId(search);
        } else {
            pets = petRepository.findByPetNameContainingIgnoreCase(search);
            owners = ownerRepository.findByPetName(search);
        }

        for (PetEntity pet : pets) {
            if (pet.getOwnerId().toString().length() == 8) {
                String formattedOwnerId = "010-" + pet.getOwnerId().toString().substring(0, 4) + "-" + pet.getOwnerId().toString().substring(4);
                pet.setFormattedOwnerId(formattedOwnerId);
            }
        }

        PetOwnerDTO petOwnerDTO = new PetOwnerDTO(pets, owners);
        return ResponseEntity.status(HttpStatus.OK).body(petOwnerDTO);

    }
    @GetMapping("/api/petDetail")
    public ResponseEntity<PetInfoDto> getPetDetail(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "petId", required = false) Long petId) {
        System.out.println("Authorization 헤더: " + authorization);
        System.out.println("petId : " + petId);
        PetInfoDto pet = petService.getPetDetails(petId);


        System.out.println("petService : " + pet);
        return ResponseEntity.ok(pet);
    }

    @PostMapping("/api/petAdd")
    public ResponseEntity<?> petAdd(
            @RequestHeader(value="Authorization") String authoriztion,
            @RequestBody PetEntity petEntity){
        petRepository.save(petEntity);
        return ResponseEntity.ok("등록 완료");
    }







}
