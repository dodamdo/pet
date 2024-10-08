package com.the.pet.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Long schId;  // 스케줄 ID (Primary Key)

    @Column(name = "pet_id", nullable = false)
    private Long petId;  // 관련된 반려동물 ID

    @Column(name = "sch_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date schDate;  // 예약 날짜

    @Column(name = "sch_time", length = 100)
    private String schTime;  // 예약 시간

    @Column(name = "sch_color", length = 10)
    private String schColor = "black";  // 예약 색상 (기본값: black)

    @Column(name = "grooming_style", length = 100)
    private String groomingStyle;  // 그루밍 스타일

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;  // 결제 방식

    @Column(name = "price")
    private int groomingPrice = 0;  // 가격 (기본값: 0)

    @Column(name = "sch_state", length = 50)
    private String schState;  // 예약 상태

    @Column(name = "sch_notes", length = 255)
    private String schNotes;  // 예약에 대한 메모


    public String getSchState() {
        return schState != null ? schState : ".";
    }
    public String getSchNotes() {
        return schNotes != null ? schNotes : ".";
    }

}


