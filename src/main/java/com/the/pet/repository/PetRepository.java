package com.the.pet.repository;


import com.the.pet.model.entity.PetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<PetEntity,Integer>{

    //검색 페이징 O
    Page<PetEntity> findByPetNameContainingIgnoreCase(String search, Pageable pageable);
    @Query("SELECT p FROM PetEntity p WHERE str(p.ownerId) LIKE %:ownerId%")
    Page<PetEntity> findByOwnerIdLike(@Param("ownerId") String ownerId, Pageable pageable);


    //검색 페이징 X
    List<PetEntity> findByPetNameContainingIgnoreCase(String search);
    @Query("SELECT p FROM PetEntity p WHERE str(p.ownerId) LIKE %:ownerId%")
    List<PetEntity> findByOwnerIdLike(@Param("ownerId") String ownerId);


    @Query("SELECT p.petName FROM PetEntity p WHERE p.petId = :petId")
    String findPetNameById(@Param("petId") Long petId);

    @Query("SELECT p.ownerId FROM PetEntity p WHERE p.petId = :petId")
    String findOwnerIdById(@Param("petId") Long petId);
}
