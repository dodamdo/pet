package com.the.pet.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor //모든 필든를 매개변수로 가지는 생성자 생성
@NoArgsConstructor //디폴트 생성자 생성
@ToString
@Getter
@Setter
@Data
@SequenceGenerator(
        name="LOGIN_ID_SEQ_GEN",//시퀀스 제너레이터 이름
        sequenceName="LOGIN_ID_SEQ",//시퀀스 이름
        initialValue=1,//시작값
        allocationSize=1//메모리를 통해 할당할 범위 사이
)
public class CommentEntity {
    @Id
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,generator="LOGIN_ID_SEQ_GEN"
    )
    private Integer id;

    @Column(name = "login_entity_id") // 로그인 엔티티 ID를 나타내는 필드
    private Integer loginEntityId; // 로그인 엔티티와의 관계를 나타내는 필드


    @Column
    private String name;

    @Column
    private String contents;






}
