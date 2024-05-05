package com.vaccinationbookingsystem.dto.responseDto;

import com.vaccinationbookingsystem.Enum.DoseType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDose1ResponseDto {

    DoseType doseType;

    String message;
}
