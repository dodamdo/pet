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
    @Column(name = "noshow_id")
    private Long noshowId;

    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Column(name = "NOSHOWCANCELDATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date noshowCancelDate;

    @Column(name = "NOSHOWCANCELINFO", nullable = false)
    private String noshowCancelInfo;


}
