package com.vaccinationbookingsystem.repository;

import com.vaccinationbookingsystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

    /*
       Note :- 1.  the return type depends on the attribute which we are passing as parameter
       whether it is unique or not.

              2. we can use basic AND OR NOT operation also
     */

    // as email id is unique attribute in person entity so it will give the single person object
    Person findByEmailId(String oldEmail);

    // in this the age is not a unique attribute, so we will get the list of all the persons who have that age
    List<Person> findByAge(int age);

    // in this we are searching with two parameters using AND operator
    // the only condition is that what we are writing first in function have to paas that attribute in parameter
    // like first is age and then name , so in parameter also we have to pass the age fist and then name
    List<Person>findByAgeAndName(int age, String name);
}
