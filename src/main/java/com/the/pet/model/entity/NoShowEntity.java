package com.the.pet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "noshow")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class NoShowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Column(name = "noshowCancelDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date noshowCancelDate;

    @Column(name = "noshowCancelInfo", nullable = false)
    private String noshowCancelInfo;


}
