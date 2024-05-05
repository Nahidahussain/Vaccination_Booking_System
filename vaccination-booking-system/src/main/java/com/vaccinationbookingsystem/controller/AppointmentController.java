package com.vaccinationbookingsystem.controller;

import com.vaccinationbookingsystem.Service.AppointmentService;
import com.vaccinationbookingsystem.dto.requestDto.BookAppointmentRequestDto;
import com.vaccinationbookingsystem.dto.responseDto.BookAppointmentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;


    @PostMapping("/book_appointment")
    public ResponseEntity<BookAppointmentResponseDto> addAppointment(@RequestBody BookAppointmentRequestDto bookAppointmentRequestDto){
        try{
            BookAppointmentResponseDto appointmentResponseDto = appointmentService.addAppointment(bookAppointmentRequestDto);
            return new ResponseEntity<>(appointmentResponseDto, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
