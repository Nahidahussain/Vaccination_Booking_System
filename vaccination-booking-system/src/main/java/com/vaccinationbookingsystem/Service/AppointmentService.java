package com.vaccinationbookingsystem.Service;

import com.vaccinationbookingsystem.dto.requestDto.BookAppointmentRequestDto;
import com.vaccinationbookingsystem.dto.responseDto.BookAppointmentResponseDto;
import com.vaccinationbookingsystem.dto.responseDto.VaccinationCenterResponseDto;
import com.vaccinationbookingsystem.exception.DoctorNotFoundException;
import com.vaccinationbookingsystem.exception.PersonNotFoundException;
import com.vaccinationbookingsystem.model.Appointment;
import com.vaccinationbookingsystem.model.Doctor;
import com.vaccinationbookingsystem.model.Person;
import com.vaccinationbookingsystem.model.VaccinationCenter;
import com.vaccinationbookingsystem.repository.AppointmentRepository;
import com.vaccinationbookingsystem.repository.DoctorRepository;
import com.vaccinationbookingsystem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public BookAppointmentResponseDto addAppointment(BookAppointmentRequestDto bookAppointmentRequestDto) {
        // first check person and doctor exists or not

        // check for person
        Optional<Person> personOptional = personRepository.findById(bookAppointmentRequestDto.getPersonId());

        if (personOptional.isEmpty()) {
            throw new PersonNotFoundException("Invalid person Id");
        }

        // check for doctor
        Optional<Doctor> doctorOptional = doctorRepository.findById(bookAppointmentRequestDto.getDoctorId());

        if (doctorOptional.isEmpty()) {
            throw new DoctorNotFoundException("Invalid doctor Id");
        }

        // if both of them exists then get them
        Person person = personOptional.get();
        Doctor doctor = doctorOptional.get();

        // converting dto to  appointment entity
        // Now i want to make an appointment, and set its values
        Appointment appointment = new Appointment();

        String appointmentId = String.valueOf(UUID.randomUUID());
        appointment.setAppointmentId(appointmentId);

        // get the dose no. info from person
        if (!person.isDose1Taken()) {
            appointment.setDoseNo(1);
        } else {
            appointment.setDoseNo(2);
        }

        appointment.setPerson(person);
        appointment.setDoctor(doctor);


        // fist save the child and then parent
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // and then do the changes
        // after setting the peron and doctor in appointment. the appointment list in both entity will get updated
        person.getAppointmentList().add(savedAppointment);
        doctor.getAppointmentList().add(savedAppointment);

        // now save them to database
        Doctor savedDoctor = doctorRepository.save(doctor);
        Person savedPerson = personRepository.save(person);


        // convert the entity to dto ,, for preparing the response dto
        BookAppointmentResponseDto bookAppointmentResponseDto = new BookAppointmentResponseDto();
        bookAppointmentResponseDto.setPersonName(person.getName());
        bookAppointmentResponseDto.setDoctorName(doctor.getName());
        bookAppointmentResponseDto.setAppointmentId(savedAppointment.getAppointmentId());
        bookAppointmentResponseDto.setAppointmentDate(savedAppointment.getAppointmentDate());

        // get the details of center from doctor
        VaccinationCenter vaccinationCenter = doctor.getVaccinationCenter();

        // now set the center details to center response dto
        VaccinationCenterResponseDto vaccinationCenterResponseDto = new VaccinationCenterResponseDto();
        vaccinationCenterResponseDto.setCenterType(vaccinationCenter.getCenterType());
        vaccinationCenterResponseDto.setCenterName(vaccinationCenter.getCenterName());
        vaccinationCenterResponseDto.setAddress(vaccinationCenter.getAddress());
        vaccinationCenterResponseDto.setMessage("Your Appointment is booked successfully for Dose - " + savedAppointment.getDoseNo());

        // and no finally set the centre response dto to appointment response dto
        bookAppointmentResponseDto.setVaccinationCenterResponseDto(vaccinationCenterResponseDto);


        // after  this  i want to send the email
        // create the object of email class
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("bluesolutions7@gmail.com");
        simpleMailMessage.setTo(savedPerson.getEmailId());
        simpleMailMessage.setSubject("Congrats !! Your Appointment is Booked !!");

        String text = "Hi " + savedPerson.getName() + "\nYour appointment has been booked with doctor " +
                savedDoctor.getName() + "." + "\nYour vaccination center name is: " + vaccinationCenter.getCenterName() +
                "\nPlease reach at this address: " + vaccinationCenter.getAddress() +
                "\nTiming of Your appointment is: " + savedAppointment.getAppointmentDate() +
                "\nYour Appointment Id is: " + savedAppointment.getAppointmentId() +
                "\n\n\n\n\n\n\n" + "\nAll the best!" + "\nBlue Solutions";


        simpleMailMessage.setText(text);

        // after setting this mail, send the mail
        javaMailSender.send(simpleMailMessage);


        // finally return the response dto
        return bookAppointmentResponseDto;
    }
}
