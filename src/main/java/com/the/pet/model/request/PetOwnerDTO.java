package com.the.pet.model.request;

import com.the.pet.model.entity.OwnerEntity;
import com.the.pet.model.entity.PetEntity;
import lombok.Getter;

import java.util.List;


@Getter
public class PetOwnerDTO {
    private List<PetEntity> pets;
    private List<OwnerEntity> owners;

    public PetOwnerDTO(List<PetEntity> pets, List<OwnerEntity> owners) {
        this.pets = pets;
        this.owners = owners;
    }

}
