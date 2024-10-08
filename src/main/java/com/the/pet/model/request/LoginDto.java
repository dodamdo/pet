package com.the.pet.model.request;

import com.the.pet.model.entity.LoginEntity;
import lombok.Data;

@Data
public class LoginDto {
    private Integer id;  // id는 null일 수 있음
    private String name;
    private String password;

    // DTO를 Entity로 변환하는 메서드
    public LoginEntity toEntity() {
        return new LoginEntity(id, name, password);
    }

    // 기본 생성자
    public LoginDto() {}

    // 이름과 비밀번호만 받는 생성자
    public LoginDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // id 포함한 생성자
    public LoginDto(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
