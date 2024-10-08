package com.the.pet.repository;


import com.the.pet.model.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<OwnerEntity,Integer>{

}
