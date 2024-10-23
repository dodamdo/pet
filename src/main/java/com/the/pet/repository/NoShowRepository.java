package com.the.pet.repository;

import com.the.pet.model.entity.NoShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoShowRepository extends JpaRepository<NoShowEntity, Long> {

    @Query("SELECT COUNT(n) FROM NoShowEntity n WHERE n.noshowCancelInfo = '노쇼' AND n.petId = :petId")
    Integer countNoShow(Long petId);

    @Query("SELECT COUNT(n) FROM NoShowEntity n WHERE n.noshowCancelInfo = '당일취소' AND n.petId = :petId")
    Integer countCancel(Long petId);

    @Query("SELECT n FROM NoShowEntity n WHERE n.noshowCancelInfo = '노쇼' AND n.petId = :petId")
    List<NoShowEntity> findNoShowList(Long petId);

    @Query("SELECT n FROM NoShowEntity n WHERE n.noshowCancelInfo = '당일취소' AND n.petId = :petId")
    List<NoShowEntity> findCancelList(Long petId);


    @Modifying
    @Query("DELETE FROM NoShowEntity n WHERE n.noshowId = :noshowId")
    void deleteNoshow(@Param("noshowId") Long noshowId);

}
