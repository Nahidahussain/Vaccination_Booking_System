package com.vaccinationbookingsystem.repository;

import com.vaccinationbookingsystem.model.Dose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoseRepository extends JpaRepository<Dose,Integer> {

}
