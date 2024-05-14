package com.vaccinationbookingsystem.Service.auth;

import com.vaccinationbookingsystem.Enum.UserRole;
import com.vaccinationbookingsystem.dto.SignUpRequestDto;
import com.vaccinationbookingsystem.dto.requestDto.AddDoctorRequestDto;
import com.vaccinationbookingsystem.dto.UserResponseDto;
import com.vaccinationbookingsystem.model.User;
import com.vaccinationbookingsystem.repository.UserRepository;
import com.vaccinationbookingsystem.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServicesImpl implements AuthServices{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * @param signupRequestDto
     * @return
     */
    @Override
    public UserResponseDto createUser(SignUpRequestDto signupRequestDto) {

        User user = User.builder()
                        .name(signupRequestDto.getName())
                        .emailId(signupRequestDto.getEmail())
                        .password(bCryptPasswordEncoder.encode(signupRequestDto.getPassword()))
                        .age(signupRequestDto.getAge())
                        .gender(signupRequestDto.getGender())
                        .userRole(signupRequestDto.getUserRole())   ///
                .build();

        User savedUser = userRepository.save(user);


        return UserTransformer.doctorToDoctorResponseDto(savedUser);
    }

    /**
     * @param emailId
     * @return
     */
    @Override
    public Boolean hasUserWithEmail(String emailId) {
        return userRepository.findFirstByEmailId(emailId).isPresent();
    }
}
