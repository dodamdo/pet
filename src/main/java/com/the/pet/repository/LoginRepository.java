package com.the.pet.repository;

import com.the.pet.model.entity.LoginEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface LoginRepository extends CrudRepository<LoginEntity,Integer> {
    @Override
    ArrayList<LoginEntity> findAll();

    //Optional<LoginEntity> findByid(Integer id);

    ArrayList<LoginEntity> findByNameContaining(String name);
}
