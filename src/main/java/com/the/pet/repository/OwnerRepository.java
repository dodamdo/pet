package com.the.pet.repository;


import com.the.pet.model.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OwnerRepository extends JpaRepository<OwnerEntity,Integer>{
    @Query("SELECT o FROM OwnerEntity o WHERE CAST(o.ownerId AS string) LIKE %:ownerId%")
    List<OwnerEntity> findByOwnerId(@Param("ownerId") String ownerId);

    @Query("SELECT o FROM OwnerEntity o WHERE o.petName LIKE %:petName%")
    List<OwnerEntity> findByPetName(@Param("petName") String petName);

    @Query("SELECT o FROM OwnerEntity o WHERE o.petId = :petId")
    List<OwnerEntity> findByPetId(@Param("petId") Long petId);

}