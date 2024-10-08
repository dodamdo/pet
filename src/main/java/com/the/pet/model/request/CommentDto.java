package com.the.pet.model.request;

import com.the.pet.model.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 생성
public class CommentDto {
    private Integer id;            // 댓글 ID (null일 수 있음)
    private Integer loginEntityId; // 로그인 엔티티 ID
    private String name;           // 댓글 작성자 이름
    private String contents;       // 댓글 내용

    public CommentEntity toEntity() {
        return new CommentEntity(id, loginEntityId, name, contents);
    }

    public CommentDto() {}

    // 로그인 엔티티 ID, 이름, 내용을 받는 생성자
    public CommentDto(Integer loginEntityId, String name, String contents) {
        this.loginEntityId = loginEntityId;
        this.name = name;
        this.contents = contents;
    }
}
