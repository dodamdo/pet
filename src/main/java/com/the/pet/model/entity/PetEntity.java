package com.the.pet.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pet")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "pet_name", nullable = false)
    private String petName;

    @Column(name = "pet_breed")
    private String petBreed;

    @Column(name = "pet_gender")
    private String petGender;

    @Column(name = "pet_age")
    private String petAge;

    @Column(name = "pet_weight")
    private String petWeight;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "pet_notes")
    private String petNotes;

    @Column(name = "is_altered")
    private String isAltered;


    public String getPetNotes() {
        return petNotes != null ? petNotes : "없음";
    }
    @Transient
    private String formattedOwnerId;
}
