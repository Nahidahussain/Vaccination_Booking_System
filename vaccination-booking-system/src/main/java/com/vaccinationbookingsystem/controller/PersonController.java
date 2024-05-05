package com.vaccinationbookingsystem.controller;


import com.vaccinationbookingsystem.Service.PersonService;
import com.vaccinationbookingsystem.dto.requestDto.AddPersonRequestDto;
import com.vaccinationbookingsystem.dto.responseDto.AddPersonResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    // By making DTOs as input parameter
    @PostMapping("/add_person")
    public ResponseEntity addPerson(@RequestBody AddPersonRequestDto addPersonRequestDto) {
        // it is not necessary that it will always get the saved person. we might receive an error also
        // in the case when email id is same,, becoz we have set the email id to be unique
        try{
            AddPersonResponseDto personResponse = personService.addPerson(addPersonRequestDto);
            return new ResponseEntity(personResponse, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity("Email already exists",HttpStatus.BAD_REQUEST);
        }
    }

    // update the email id of a person
    @PutMapping("update_email")
    public ResponseEntity updateEmail(@RequestParam("oldEmail") String oldEmail,
                                      @RequestParam("newEmail") String newEmail){

        try{
            AddPersonResponseDto emailResponse =  personService.updateEmail(oldEmail,newEmail);
            return new ResponseEntity(emailResponse,HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
