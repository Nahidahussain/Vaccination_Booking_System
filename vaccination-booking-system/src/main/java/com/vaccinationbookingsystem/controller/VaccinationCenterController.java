package com.vaccinationbookingsystem.controller;

import com.vaccinationbookingsystem.Service.VaccinationCenterService;
import com.vaccinationbookingsystem.dto.requestDto.VaccinationCenterRequestDto;
import com.vaccinationbookingsystem.dto.responseDto.VaccinationCenterResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaccination_centre")
public class VaccinationCenterController {

    @Autowired
    VaccinationCenterService vaccinationCenterService;


    // add vaccination centre
    @PostMapping("/add_vaccine_centre")
    public ResponseEntity addVaccinationCentre(@RequestBody VaccinationCenterRequestDto vaccinationCentreRequestDto){
        try{
            VaccinationCenterResponseDto vaccinationResponse = vaccinationCenterService.addVaccinationCentre(vaccinationCentreRequestDto);
            return new ResponseEntity(vaccinationResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<String> getVaccineHello(){
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
}
