package com.the.pet.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "owner")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OwnerEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "main_owner_phone", nullable = false, length = 100)
    private String mainOwnerPhone;

    @Column(name = "main_owner_name", nullable = false, length = 100)
    private String mainOwnerName;

    @Column(name = "main_owner_notes", length = 255)
    private String mainOwnerNotes ; // 기본값 설정

    @Column(name = "second_owner_phone", length = 100)
    private String secondOwnerPhone;

    @Column(name = "second_owner_name", length = 100)
    private String secondOwnerName;

    @Column(name = "second_owner_notes", length = 255)
    private String secondOwnerNotes;

    @Column(name = "third_owner_phone", length = 100)
    private String thirdOwnerPhone;

    @Column(name = "third_owner_name", length = 100)
    private String thirdOwnerName;

    @Column(name = "third_owner_notes", length = 255)
    private String thirdOwnerNotes;


    public String getMainOwnerNotes() {
        return mainOwnerNotes != null ? mainOwnerNotes : "없음";
    }
    public String getSecondOwnerNotes() {
        return secondOwnerNotes != null ? secondOwnerNotes : "없음";
    }
    public String getThirdOwnerNotes() {
        return thirdOwnerNotes != null ? thirdOwnerNotes : "없음";
    }
}
