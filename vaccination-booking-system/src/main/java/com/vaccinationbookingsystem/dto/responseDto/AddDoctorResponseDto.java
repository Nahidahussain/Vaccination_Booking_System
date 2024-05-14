package com.vaccinationbookingsystem.dto.responseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddDoctorResponseDto {

    String name;

    String message;

    VaccinationCenterResponseDto vaccinationCenterResponseDto;
}
