package com.vaccinationbookingsystem.exception;

public class DoctorIsAlreadyPresent extends RuntimeException{

    public DoctorIsAlreadyPresent(String message) {
        super(message);
    }
}
