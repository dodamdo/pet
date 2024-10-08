package com.the.pet.repository;

import com.the.pet.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface CommentRepository extends CrudRepository<CommentEntity,Integer> {
    @Override
    ArrayList<CommentEntity> findAll();
    @Query(value =
            "SELECT * " +
                    "FROM comment_entity " +
                    "WHERE login_entity_id = :loginEntityId", nativeQuery = true)
    ArrayList<CommentEntity> findByLoginEntityId(@Param("loginEntityId")
                                                 Integer loginEntityId);
    ArrayList<CommentEntity> findByNameContaining(String name);
}
