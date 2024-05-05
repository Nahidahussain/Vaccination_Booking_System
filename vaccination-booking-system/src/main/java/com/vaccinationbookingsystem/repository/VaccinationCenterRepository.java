package com.vaccinationbookingsystem.repository;

import com.vaccinationbookingsystem.model.VaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationCenterRepository extends JpaRepository<VaccinationCenter,Integer> {

    List<VaccinationCenter> findByCenterName(String centerName);
}
