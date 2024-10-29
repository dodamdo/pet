package com.the.pet.repository;


import com.the.pet.model.entity.SchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SchRepository extends JpaRepository<SchEntity,Integer>{


    @Query("SELECT s FROM SchEntity s WHERE s.schDate >= :startDate AND s.schDate < :nextMonthStart ORDER BY s.schDate ASC, s.schTime ASC")
    List<SchEntity> findSchedulesBetween(@Param("startDate") LocalDate startDate, @Param("nextMonthStart") LocalDate nextMonthStart);

    List<SchEntity> findBySchDateOrderBySchTimeAsc(LocalDate schDate);
    List<SchEntity> findByPetIdOrderBySchDateAsc(Integer petId);
    List<SchEntity> findByPetIdOrderBySchDateDesc(Integer petId);

    @Query("SELECT SUM(s.groomingPrice) FROM SchEntity s WHERE s.paymentMethod = :paymentMethod AND s.schDate >= :startDate AND s.schDate < :nextMonthStart")
    Integer  findTotalPriceByPaymentMethod(@Param("paymentMethod") String paymentMethod, @Param("startDate") LocalDate startDate, @Param("nextMonthStart") LocalDate nextMonthStart);

    @Query("SELECT SUM(s.groomingPrice) FROM SchEntity s WHERE s.paymentMethod = :paymentMethod AND s.schDate >= :startDate AND s.schDate <= :endDate")
    Integer  TotalPriceByPaymentMethod(@Param("paymentMethod") String paymentMethod, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    SchEntity findBySchId(Long schId);

    @Query("SELECT s.photoUrl FROM SchEntity s WHERE s.petId = :petId AND s.photoUrl IS NOT NULL")
    List<String> findPhotoUrlsByPetId(@Param("petId") Long petId);


    @Query("SELECT s FROM SchEntity s WHERE s.petId = :petId ORDER BY s.schDate DESC")
    List<SchEntity> findLastGroomingByPetId(@Param("petId") Long petId);





}
