package com.the.pet.repository;


import com.the.pet.model.entity.PetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity,Integer>{


    Page<PetEntity> findByPetNameContainingIgnoreCase(String search, Pageable pageable);
}
