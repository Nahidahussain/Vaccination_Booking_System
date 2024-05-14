package com.vaccinationbookingsystem.transformer;

import com.vaccinationbookingsystem.dto.UserResponseDto;
import com.vaccinationbookingsystem.model.User;

public class UserTransformer {

    public static UserResponseDto doctorToDoctorResponseDto(User user){
        return UserResponseDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .id(user.getId())
                .emailId(user.getEmailId())
                .gender(user.getGender())
                .password(user.getPassword())
                .userRole(user.getUserRole())
                .build();
    }
}
