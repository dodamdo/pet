package com.the.pet.model.request;

import com.the.pet.model.entity.OwnerEntity; // OwnerEntity의 경로를 수정해주세요.
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data // @Data 어노테이션을 사용하면 getter, setter, toString 등을 자동으로 생성합니다.
public class OwnerDto {
    private Long ownerId;
    private Long shopId;
    private String mainOwnerPhone;
    private String mainOwnerName;
    private String mainOwnerNotes;
    private String secondOwnerPhone;
    private String secondOwnerName;
    private String secondOwnerNotes;
    private String thirdOwnerPhone;
    private String thirdOwnerName;
    private String thirdOwnerNotes;


}
