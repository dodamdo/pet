package com.the.pet.service;

import com.the.pet.model.entity.PetEntity;
import com.the.pet.model.entity.SchEntity;
import com.the.pet.model.request.PetInfoDto;
import com.the.pet.repository.NoShowRepository;
import com.the.pet.repository.PetRepository;
import com.the.pet.repository.SchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private SchRepository schRepository;
    @Autowired
    private NoShowRepository noShowRepository;


    public List<PetEntity> getAllPets() {
        return petRepository.findAll();
    }

    public void savePet(PetEntity pet) {
        petRepository.save(pet);
    }

    public PetEntity getPetById(Long petId) {
        return petRepository.findById(Math.toIntExact(petId)).orElse(null);
    }



    public PetInfoDto getPetDetails(Long petId) {
        PetEntity pet = petRepository.findById(Math.toIntExact(petId)).orElseThrow(() -> new RuntimeException("Pet not found"));
        List<SchEntity> lastGroomings = schRepository.findLastGroomingByPetId(pet.getPetId());

        SchEntity lastGrooming = lastGroomings.isEmpty() ? null : lastGroomings.get(0);
        LocalDate groomingDate = lastGrooming != null ? lastGrooming.getSchDate() : null;
        String groomingStyle = lastGrooming != null ? lastGrooming.getGroomingStyle() : "정보 없음";
        String photoUrl = lastGrooming != null ? lastGrooming.getPhotoUrl() : null;

        int noShowCount = noShowRepository.countNoShow(petId);  // 노쇼 카운트

        return new PetInfoDto(
                pet.getPetId(),
                pet.getPetName(),
                pet.getPetBreed(),
                pet.getOwnerId(),
                groomingDate,
                groomingStyle,
                photoUrl,
                noShowCount
        );
    }

    public List<PetInfoDto> getAllPetDetails() {
        List<PetEntity> pets = petRepository.findAll();  // 모든 펫 정보 가져오기
        List<PetInfoDto> petDetails = new ArrayList<>();

        for (PetEntity pet : pets) {
            List<SchEntity> lastGroomings = schRepository.findLastGroomingByPetId(pet.getPetId());  // 최근 미용 정보 가져오기
            SchEntity lastGrooming = lastGroomings.isEmpty() ? null : lastGroomings.get(0); // 가장 최근 미용 정보
            LocalDate groomingDate = lastGrooming != null ? lastGrooming.getSchDate() : null;
            String groomingStyle = lastGrooming != null ? lastGrooming.getGroomingStyle() : "정보 없음";
            String photoUrl = lastGrooming != null ? lastGrooming.getPhotoUrl() : null;

            int noShowCount = noShowRepository.countNoShow(pet.getPetId());  // 노쇼 카운트

            PetInfoDto dto = new PetInfoDto(
                    pet.getPetId(),
                    pet.getPetName(),
                    pet.getPetBreed(),
                    pet.getOwnerId(),
                    groomingDate,
                    groomingStyle,
                    photoUrl,
                    noShowCount
            );

            petDetails.add(dto);
        }

        petDetails.sort(Comparator.comparing(PetInfoDto::getPetId));

        return petDetails;
    }



}
