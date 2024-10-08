package com.the.pet.service;

import com.the.pet.model.entity.PetEntity;
import com.the.pet.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public List<PetEntity> getAllPets() {
        return petRepository.findAll();
    }

    public void savePet(PetEntity pet) {
        petRepository.save(pet);
    }
}
