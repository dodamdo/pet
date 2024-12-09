package com.the.pet.model.request;

import com.the.pet.model.entity.SchEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PetSchDto {

    private PetInfoDto petInfo;
    private List<SchEntity> schedules;

}
