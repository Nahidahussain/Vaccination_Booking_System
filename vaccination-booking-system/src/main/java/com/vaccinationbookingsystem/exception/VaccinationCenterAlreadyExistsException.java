package com.vaccinationbookingsystem.exception;

public class VaccinationCenterAlreadyExistsException extends RuntimeException{

    public VaccinationCenterAlreadyExistsException(String message) {
        super(message);
    }
}
