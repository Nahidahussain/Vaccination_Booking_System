package com.vaccinationbookingsystem.dto;

import com.vaccinationbookingsystem.Enum.Gender;
import com.vaccinationbookingsystem.Enum.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDto {

    Long id;
    String name;

    int age;

    String emailId;

    Gender gender;

    String password;

    UserRole userRole;
}
