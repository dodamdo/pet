package com.the.pet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ReserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reser_id")
    private Long reserId;

    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    private String petName;

    @Column(name = "reser_date", nullable = false)
    private LocalDate reserDate;

    @Column(name = "reser_time", length = 100)
    private String reserTime;

    @Column(name = "reser_color", length = 100)
    private String reserColor = "black";

    @Column(name = "reser_grooming", length = 100)
    private String reserGroomingStyle;

    @Column(name = "reser_notes", length = 255)
    private String reserNotes;




}


