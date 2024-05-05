package com.vaccinationbookingsystem.dto.responseDto;

import com.vaccinationbookingsystem.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorResponseByAgeDto {

    String name;

    String email;

    int age;

    Gender gender;

    VaccinationCenterResponseDto vaccinationCenterResponseDto;
}
