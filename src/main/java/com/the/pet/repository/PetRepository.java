package com.the.pet.repository;


import com.the.pet.model.entity.PetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetRepository extends JpaRepository<PetEntity,Integer>{

    Page<PetEntity> findByPetNameContainingIgnoreCase(String search, Pageable pageable);

    @Query("SELECT p FROM PetEntity p WHERE str(p.ownerId) LIKE %:ownerId%")
    Page<PetEntity> findByOwnerIdLike(@Param("ownerId") String ownerId, Pageable pageable);

    @Query("SELECT p.petName FROM PetEntity p WHERE p.petId = :petId")
    String findPetNameById(@Param("petId") Long petId);

    @Query("SELECT p.ownerId FROM PetEntity p WHERE p.petId = :petId")
    String findOwnerIdById(@Param("petId") Long petId);
}
