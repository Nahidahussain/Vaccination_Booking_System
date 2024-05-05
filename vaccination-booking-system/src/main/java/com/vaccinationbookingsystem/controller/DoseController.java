package com.vaccinationbookingsystem.controller;

import com.vaccinationbookingsystem.Service.DoseService;
import com.vaccinationbookingsystem.dto.requestDto.BookDose1RequestDto;
import com.vaccinationbookingsystem.dto.responseDto.BookDose1ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dose")
public class DoseController {

    @Autowired
    DoseService doseService;

    // By Taking Request DTO's as input
    @PostMapping("/get_dose_1")
    public ResponseEntity getDose_1(@RequestBody BookDose1RequestDto bookDose1RequestDto){
        try{
            BookDose1ResponseDto doseTake = doseService.getDose_1(bookDose1RequestDto);
            return new ResponseEntity(doseTake, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
