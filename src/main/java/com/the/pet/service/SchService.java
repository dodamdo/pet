package com.the.pet.service;

import com.the.pet.model.entity.OwnerEntity;
import com.the.pet.model.entity.PetEntity;
import com.the.pet.model.entity.SchEntity;
import com.the.pet.repository.OwnerRepository;
import com.the.pet.repository.PetRepository;
import com.the.pet.repository.SchRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class SchService {
    @Autowired
    private SchRepository schRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public List<SchEntity> getAllSch(){
        return schRepository.findAll();
    }


    public List<SchEntity> getSchedulesForNextMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1); // 지정한 달의 첫날
        LocalDate nextMonthStart = startDate.plusMonths(1); // 다음 달의 첫날
        return schRepository.findSchedulesBetween(startDate, nextMonthStart);
    }

}
