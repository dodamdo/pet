package com.the.pet.service;

import com.the.pet.repository.OwnerRepository;
import com.the.pet.model.entity.OwnerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    public List<OwnerEntity> getAllOwners() {
        return ownerRepository.findAll();
    }
}
