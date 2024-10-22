package com.the.pet.repository;

import com.the.pet.model.entity.NoShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoShowRepository extends JpaRepository<NoShowEntity, Long> {

    @Query("SELECT COUNT(n) FROM NoShowEntity n WHERE n.noshowCancelInfo = '노쇼' AND n.petId = :petId")
    Integer countNoShow(Integer petId);

    @Query("SELECT COUNT(n) FROM NoShowEntity n WHERE n.noshowCancelInfo = '당일취소' AND n.petId = :petId")
    Integer countCancel(Integer petId);

    @Query("SELECT n FROM NoShowEntity n WHERE n.noshowCancelInfo = '노쇼' AND n.petId = :petId")
    List<NoShowEntity> findNoShowList(Integer petId);

    @Query("SELECT n FROM NoShowEntity n WHERE n.noshowCancelInfo = '당일취소' AND n.petId = :petId")
    List<NoShowEntity> findSCancelList(Integer petId);
}
