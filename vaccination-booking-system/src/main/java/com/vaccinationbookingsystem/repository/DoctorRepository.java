package com.vaccinationbookingsystem.repository;

import com.vaccinationbookingsystem.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    // custom methods of jpa (second method)
    Doctor findByEmailId(String emailId);


    // custom query method of jpa (third method)

    //@Query(value = "select d from Doctor as d where d.age > : age ") this is when we don't have to write nativeQuery
    @Query(value = "select * from doctor where age > :age",nativeQuery = true)
    List<Doctor> getDoctorByAgeGreaterThan(int age);
}
