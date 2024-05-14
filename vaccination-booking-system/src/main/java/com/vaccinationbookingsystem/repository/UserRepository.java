package com.vaccinationbookingsystem.repository;

import com.vaccinationbookingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findFirstByEmailId(String username);
}
