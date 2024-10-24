package com.the.pet.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "extraowner")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OwnerEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extraowner_id ", nullable = false)
    private Long extraownerId ;

    @Column(name = "pet_id" , nullable = false)
    private Long petId;

    @Column(name = "pet_name" , nullable = false)
    private String petName;

    @Column(name = "owner_id " , nullable = false)
    private Long ownerId ;


    @Transient
    private String formattedOwnerId;

    @PostLoad
    private void updateFormattedOwnerId() {
        this.formattedOwnerId = formatOwnerId(this.ownerId);
    }

    private String formatOwnerId(Long ownerId) {
        if (ownerId == null) {
            return null;
        }
        String ownerIdStr = String.valueOf(ownerId);
        while (ownerIdStr.length() < 8) {
            ownerIdStr = "0" + ownerIdStr;
        }
        return "010-" + ownerIdStr.substring(0, 4) + "-" + ownerIdStr.substring(4);
    }
}
