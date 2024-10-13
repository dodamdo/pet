package com.the.pet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "schedule")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sch_id")
    private Long schId;

    @Column(name = "pet_id", nullable = false)
    private Long petId;

    private String petName;
    private String OwnerId;

    @Column(name = "sch_date", nullable = false)
    private LocalDate schDate;

    @Column(name = "sch_time", length = 100)
    private String schTime;

    @Column(name = "sch_color", length = 10)
    private String schColor = "black";

    @Column(name = "grooming_style", length = 100)
    private String groomingStyle;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "price")
    private int groomingPrice = 0;

    @Column(name = "sch_state", length = 50)
    private String schState;

    @Column(name = "sch_notes", length = 255)
    private String schNotes;


    public String getSchState() {
        return schState != null ? schState : ".";
    }
    public String getSchNotes() {
        return schNotes != null ? schNotes : ".";
    }

}


