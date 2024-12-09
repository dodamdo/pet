package com.the.pet.repository;

import com.the.pet.model.entity.ReserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReserRepository extends JpaRepository<ReserEntity,Integer> {

    List<ReserEntity> findAllByReserDateBetween(LocalDate startDate, LocalDate endDate, Sort sort);

    List<ReserEntity> findByReserDateOrderByReserTimeAsc(LocalDate date);

    @Query("SELECT r FROM ReserEntity r WHERE r.reserDate = :tomorrow")
    List<ReserEntity> findReservationsForTomorrow(@Param("tomorrow") LocalDate tomorrow);

}
