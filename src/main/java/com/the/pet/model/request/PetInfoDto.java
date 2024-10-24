package com.the.pet.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PetInfoDto {
    private Long petId;
    private String petName;
    private Long ownerId;
    private LocalDate lastGroomingDate;
    private String lastGroomingStyle;
    private int noShowCount;

}