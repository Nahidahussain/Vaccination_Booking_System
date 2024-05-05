package com.vaccinationbookingsystem.Service;

import com.vaccinationbookingsystem.dto.requestDto.BookDose1RequestDto;
import com.vaccinationbookingsystem.dto.responseDto.BookDose1ResponseDto;
import com.vaccinationbookingsystem.exception.DoseAlreadyTakenException;
import com.vaccinationbookingsystem.exception.PersonNotFoundException;
import com.vaccinationbookingsystem.model.Dose;
import com.vaccinationbookingsystem.model.Person;
import com.vaccinationbookingsystem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DoseService {

    // i have to check that person exists or not so i will autowired the person repository
    @Autowired
    PersonRepository personRepository;


    // This is by taking Request DTOs
    public BookDose1ResponseDto getDose_1(BookDose1RequestDto bookDose1RequestDto) {
        // find the person to check he is in database or not
        Optional<Person> optionalPerson = personRepository.findById(bookDose1RequestDto.getPersonId());

        // if that person is not in the database then throw the exception
        if(!optionalPerson.isPresent()){
            throw new PersonNotFoundException("Invalid PersonId");
        }

        //now , if that person exists , then get the person
        Person person = optionalPerson.get();

        // check whether that person has already taken dose_1 or not
        if(person.isDose1Taken()){
            throw new DoseAlreadyTakenException("You have Already taken Dose - 1");
        }

        // if the person have not taken the dose then he will take that dose
        // create a dose from request Dto
        Dose dose = new Dose();
        dose.setDoseId(String.valueOf(UUID.randomUUID()));  // setting dose id
        dose.setDoseType(bookDose1RequestDto.getDoseType());   // setting the type of the dose
        dose.setPerson(person);   // setting the person to the dose

        // after creating and setting the dose , set that, this person has taken the dose
        person.setDose1Taken(true);
        // and  now save the dose to the person
        person.getDosesTaken().add(dose);
        // and save that person to the person repository  also
        Person savedPerson = personRepository.save(person);

        // now i want to return the dose , so i will get it in the person repos
//        return savedPerson.getDosesTaken().get(0);

        // i want to return the response dto
        BookDose1ResponseDto bookDose1ResponseDto = new BookDose1ResponseDto();
        bookDose1ResponseDto.setDoseType(dose.getDoseType());
        bookDose1ResponseDto.setMessage("Your have successfully taken dose -1");

        return bookDose1ResponseDto;
    }
}
