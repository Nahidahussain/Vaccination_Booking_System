package com.vaccinationbookingsystem.dto;

import com.vaccinationbookingsystem.Enum.Gender;
import com.vaccinationbookingsystem.Enum.UserRole;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequestDto {

    String email;
    String password;
    String name;
    int age;
    Gender gender;
    UserRole userRole;

}
