package com.vaccinationbookingsystem.Service.auth;

import com.vaccinationbookingsystem.dto.SignUpRequestDto;
import com.vaccinationbookingsystem.dto.requestDto.AddDoctorRequestDto;
import com.vaccinationbookingsystem.dto.UserResponseDto;

public interface AuthServices {

    UserResponseDto createUser (SignUpRequestDto signupRequestDto);
    Boolean hasUserWithEmail(String emailId);
}
