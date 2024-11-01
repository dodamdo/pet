package com.the.pet.repository;

import com.the.pet.model.entity.ReserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReserRepository extends JpaRepository<ReserEntity,Integer> {

    List<ReserEntity> findAllByReserDateBetween(LocalDate startDate, LocalDate endDate, Sort sort);

    List<ReserEntity> findByReserDateOrderByReserTimeAsc(LocalDate date);

}
