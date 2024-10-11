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




    public String getMainOwnerNotes() {
        return mainOwnerNotes != null ? mainOwnerNotes : "없음";
    }
}
