package com.vaccinationbookingsystem.dto.responseDto;

import com.vaccinationbookingsystem.Enum.CenterType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VaccinationCenterResponseDto {

    CenterType centerType;

    String centerName;

    String address;

    String message;
}
