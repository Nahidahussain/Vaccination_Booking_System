package com.vaccinationbookingsystem.dto.responseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookAppointmentResponseDto {
    String personName;
    String doctorName;
    String AppointmentId;
    Date appointmentDate;

    VaccinationCenterResponseDto vaccinationCenterResponseDto;

}
