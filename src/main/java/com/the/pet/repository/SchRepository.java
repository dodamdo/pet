package com.the.pet.repository;


import com.the.pet.model.entity.PetEntity;
import com.the.pet.model.entity.SchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SchRepository extends JpaRepository<SchEntity,Integer>{


    @Query("SELECT s FROM SchEntity s WHERE s.schDate >= :startDate AND s.schDate < :nextMonthStart")
    List<SchEntity> findSchedulesBetween(@Param("startDate") LocalDate startDate, @Param("nextMonthStart") LocalDate nextMonthStart);

    List<SchEntity> findBySchDateOrderBySchTimeAsc(LocalDate schDate);

}
