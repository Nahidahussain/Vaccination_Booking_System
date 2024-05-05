package com.vaccinationbookingsystem.Service;

import com.vaccinationbookingsystem.dto.requestDto.AddPersonRequestDto;
import com.vaccinationbookingsystem.dto.responseDto.AddPersonResponseDto;
import com.vaccinationbookingsystem.exception.PersonNotFoundException;
import com.vaccinationbookingsystem.model.Person;
import com.vaccinationbookingsystem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public AddPersonResponseDto addPerson(AddPersonRequestDto addPersonRequestDto) {

        // Before saving the AddPersonRequestDto to database
        // we first have to convert the Request dto to entity

        // convert AddPersonRequestDto to ---> person entity

        // make a new class and set the parameter which needed to be set
        // just see that the parameter of person class will come from where,
        // and which parameter,i will have to set
        Person person = new Person();
        person.setName(addPersonRequestDto.getName());
        person.setAge(addPersonRequestDto.getAge());
        person.setEmailId(addPersonRequestDto.getEmailId());
        person.setGender(addPersonRequestDto.getGender());
        person.setDose1Taken(false);
        person.setDose2Taken(false);
        person.setCertificate(null);

        // save the person to database
        Person savedPerson = personRepository.save(person);

        // Now we have the return the AddPersonResponse DTO
        // Convert Entity to ---> DTO
        AddPersonResponseDto addPersonResponseDto = new AddPersonResponseDto();
        addPersonResponseDto.setName(person.getName());
        addPersonResponseDto.setMessage("Congrats you have registered successfully !!");

        // now return the DTO
        return addPersonResponseDto;


    }

    public AddPersonResponseDto updateEmail(String oldEmail, String newEmail) {
        // get the person , by using custom method findByEMailId()
        Person person  = personRepository.findByEmailId(oldEmail);

        // if that person does not exists then return null
        if(person == null){
            throw new PersonNotFoundException("Sorry this email id does not exists ");
        }

        // if that person exists then set the new email id for that person
        person.setEmailId(newEmail);

        // now save the person
        Person savedPerson = personRepository.save(person);

        // prepare response dto for updateEmail
        AddPersonResponseDto addPersonResponseDto = new AddPersonResponseDto();
        addPersonResponseDto.setName(savedPerson.getName());
        addPersonResponseDto.setMessage("email updated successfully !!");

        // return the response dto
        return addPersonResponseDto;
//        return "email updated successfully";
    }
}
