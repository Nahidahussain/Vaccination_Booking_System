package com.vaccinationbookingsystem.exception;

public class DoseAlreadyTakenException extends RuntimeException{

    public DoseAlreadyTakenException(String message){
        super(message);
    }
}
