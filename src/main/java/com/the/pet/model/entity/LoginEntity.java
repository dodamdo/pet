package com.the.pet.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@SequenceGenerator(
        name = "LOGIN_ID_SEQ_GEN",    // 자바 코드에서 사용할 시퀀스 제너레이터 이름
        sequenceName = "LOGIN_ID_SEQ",// 오라클에서의 시퀀스 이름
        initialValue = 1,             // 시작값
        allocationSize = 1            // 메모리를 통해 할당할 범위 사이즈
)
public class LoginEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, // 시퀀스 전략 사용
            generator = "LOGIN_ID_SEQ_GEN"      // 설정해 놓은 시퀀스 제너레이터 이름
    )
    private Integer id;

    @Column
    private String name;

    @Column
    private String password;

}
