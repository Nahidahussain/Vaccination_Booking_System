package com.vaccinationbookingsystem.model;

import com.vaccinationbookingsystem.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    int age;

    @Column(unique = true,nullable = false)
    //  @Column(unique = true,nullable = false)
    // now this column became unique, and user have to pass the email id ,se we set to nullable
    String emailId;

    @Enumerated(EnumType.STRING)
    // i am telling mysql that gender is enum and we are storing as string
    Gender gender;

    boolean Dose1Taken;

    boolean Dose2Taken;

    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL)
    //means one person can have multiple doses
    // and if a person is having multiple doses then i need a data structure to store multiple doses, so i use list
    List<Dose> dosesTaken = new ArrayList<>(); // becoz initially person ne 0 dose lagayi hogi, isliye humne yahi initialize kar diya yaha

    @OneToOne(mappedBy = "person",cascade = CascadeType.ALL)
    Certificate certificate;

    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL )
    List<Appointment> appointmentList = new ArrayList<>();

}
