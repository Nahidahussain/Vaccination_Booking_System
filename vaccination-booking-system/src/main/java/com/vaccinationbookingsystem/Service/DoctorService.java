package com.vaccinationbookingsystem.Service;

import com.vaccinationbookingsystem.dto.requestDto.AddDoctorRequestDto;
import com.vaccinationbookingsystem.dto.responseDto.AddDoctorResponseDto;
import com.vaccinationbookingsystem.dto.responseDto.DoctorResponseByAgeDto;
import com.vaccinationbookingsystem.dto.responseDto.VaccinationCenterResponseDto;
import com.vaccinationbookingsystem.exception.DoctorIsAlreadyPresent;
import com.vaccinationbookingsystem.exception.DoctorNotFoundException;
import com.vaccinationbookingsystem.exception.VaccinationCenterDoesNotExists;
import com.vaccinationbookingsystem.model.Doctor;
import com.vaccinationbookingsystem.model.VaccinationCenter;
import com.vaccinationbookingsystem.repository.DoctorRepository;
import com.vaccinationbookingsystem.repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;

    public AddDoctorResponseDto addDoctor(AddDoctorRequestDto addDoctorRequestDto) {

        // first check center exists or not
        // if not , then throw  exception
        Optional<VaccinationCenter> vaccinationCenterOptional = vaccinationCenterRepository.findById(addDoctorRequestDto.getCenterId());
        if(vaccinationCenterOptional.isEmpty()){
            throw new VaccinationCenterDoesNotExists("Vaccination center does not exists");
        }

        // if vaccination center exists, then get the vaccination center
        VaccinationCenter vaccinationCenter = vaccinationCenterOptional.get();

        // now convert the addDoctor DTO to Doctor entity
        Doctor doctor = new Doctor();
        doctor.setName(addDoctorRequestDto.getName());
        doctor.setAge(addDoctorRequestDto.getAge());
        doctor.setEmailId(addDoctorRequestDto.getEmailId());
        doctor.setGender(addDoctorRequestDto.getGender());
        doctor.setVaccinationCenter(vaccinationCenter);

        // before adding this doctor to center make sure this doctor is already not added
        // if doctor is already registered , throw exception
        Doctor DoctorPresent = doctorRepository.findByEmailId(doctor.getEmailId());
        if(DoctorPresent != null){
            throw  new DoctorIsAlreadyPresent("Doctor is already registered");
        }

        // Note :- before saving any entity to database make sure that
        //  we have set all the attributes which it required, and also
        // make sure that ,when we are setting any other entity , do we
        // need to change anything in it or not

        // if doctor is registering for first time then add doctor in vaccinationCenter's doctorList
        vaccinationCenter.getDoctorList().add(doctor);

        // now save the parent entity i.e vaccination center
        VaccinationCenter savedCenter = vaccinationCenterRepository.save(vaccinationCenter);  // this will save both center and doctor


        // Now prepare the response DTO,, convert the entity to DTO
        // i have to get the savedDoctor first
        List<Doctor> doctorList = savedCenter.getDoctorList();
        Doctor savedDoctor = doctorList.get(doctorList.size()-1);

        AddDoctorResponseDto addDoctorResponseDto = new AddDoctorResponseDto();
        addDoctorResponseDto.setName(savedDoctor.getName());
        addDoctorResponseDto.setMessage("Congrats !! , You have been register");

        VaccinationCenterResponseDto vaccinationCenterResponseDto = new VaccinationCenterResponseDto();
        vaccinationCenterResponseDto.setCenterName(savedCenter.getCenterName());
        vaccinationCenterResponseDto.setCenterType(savedCenter.getCenterType());
        vaccinationCenterResponseDto.setAddress(savedCenter.getAddress());
        vaccinationCenterResponseDto.setMessage("ThankYou for registering to this center !!");

        addDoctorResponseDto.setVaccinationCenterResponseDto(vaccinationCenterResponseDto);

        // return the prepared response DTO
        return addDoctorResponseDto;
    }

    public List<DoctorResponseByAgeDto> getDoctorsByAgeGreaterThan(int age) {
        // get the list of doctors with the age greater than given age
        List<Doctor> doctorList = doctorRepository.getDoctorByAgeGreaterThan(age);

        // if the doctor list is empty then throw new exception
        if(doctorList.isEmpty()){
            throw  new DoctorNotFoundException("No Doctor found above this age !!");
        }

        // make a response dto list
        List<DoctorResponseByAgeDto>  doctorResponseByAgeDtosList = new ArrayList<>();

        // iterate over doctor list and get the doctor and for doctor prepare the dto and add that dto to list
        for(Doctor doctor : doctorList){
            // make a new response dto;
            DoctorResponseByAgeDto doctorResponseByAgeDto = new DoctorResponseByAgeDto();

            //setting the doctor details to dto
            doctorResponseByAgeDto.setName(doctor.getName());
            doctorResponseByAgeDto.setEmail(doctor.getEmailId());
            doctorResponseByAgeDto.setAge(doctor.getAge());
            doctorResponseByAgeDto.setGender(doctor.getGender());

            VaccinationCenterResponseDto vaccinationCenterResponseDto = new VaccinationCenterResponseDto();
            vaccinationCenterResponseDto.setCenterName(doctor.getVaccinationCenter().getCenterName());
            vaccinationCenterResponseDto.setCenterType(doctor.getVaccinationCenter().getCenterType());
            vaccinationCenterResponseDto.setAddress(doctor.getVaccinationCenter().getAddress());
            vaccinationCenterResponseDto.setMessage("Thanks for coming to My Profile");

            doctorResponseByAgeDto.setVaccinationCenterResponseDto(vaccinationCenterResponseDto);

            // now add the response dto list
            doctorResponseByAgeDtosList.add(doctorResponseByAgeDto);

        }

        return doctorResponseByAgeDtosList;
    }
}
