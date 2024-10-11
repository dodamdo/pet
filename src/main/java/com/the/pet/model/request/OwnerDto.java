package com.the.pet.model.request;

import com.the.pet.model.entity.OwnerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OwnerDto {
    private Long ownerId;
    private Long shopId;
    private String mainOwnerPhone;
    private String mainOwnerName;
    private String mainOwnerNotes;


}
